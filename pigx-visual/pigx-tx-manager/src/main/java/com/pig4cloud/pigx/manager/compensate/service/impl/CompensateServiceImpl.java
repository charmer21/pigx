/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.manager.compensate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lorne.core.framework.exception.ServiceException;
import com.lorne.core.framework.utils.DateUtil;
import com.lorne.core.framework.utils.encode.Base64Utils;
import com.lorne.core.framework.utils.http.HttpUtils;
import com.pig4cloud.pigx.manager.compensate.dao.CompensateDao;
import com.pig4cloud.pigx.manager.compensate.model.TransactionCompensateMsg;
import com.pig4cloud.pigx.manager.compensate.model.TxModel;
import com.pig4cloud.pigx.manager.compensate.service.CompensateService;
import com.pig4cloud.pigx.manager.config.ConfigReader;
import com.pig4cloud.pigx.manager.manager.ModelInfoManager;
import com.pig4cloud.pigx.manager.manager.service.TxManagerSenderService;
import com.pig4cloud.pigx.manager.manager.service.TxManagerService;
import com.pig4cloud.pigx.manager.model.ModelInfo;
import com.pig4cloud.pigx.manager.model.ModelName;
import com.pig4cloud.pigx.manager.netty.model.TxGroup;
import com.pig4cloud.pigx.manager.netty.model.TxInfo;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * @author LCN on 2017/11/11
 */
@Service
public class CompensateServiceImpl implements CompensateService {
	private static final String SUCCESS = "success";
	private static final String SUCCESS1 = "SUCCESS";
	private Logger logger = LoggerFactory.getLogger(CompensateServiceImpl.class);

	@Autowired
	private CompensateDao compensateDao;

	@Autowired
	private ConfigReader configReader;

	@Autowired
	private TxManagerSenderService managerSenderService;

	@Autowired
	private TxManagerService managerService;

	@Autowired
	private Executor threadPool;

	@Override
	public boolean saveCompensateMsg(final TransactionCompensateMsg transactionCompensateMsg) {

		TxGroup txGroup = managerService.getTxGroup(transactionCompensateMsg.getGroupId());
		if (txGroup == null) {
			//仅发起方异常，其他模块正常
			txGroup = new TxGroup();
			txGroup.setNowTime(System.currentTimeMillis());
			txGroup.setGroupId(transactionCompensateMsg.getGroupId());
			txGroup.setIsCompensate(1);
		} else {
			managerService.deleteTxGroup(txGroup);
		}

		transactionCompensateMsg.setTxGroup(txGroup);

		final String json = JSON.toJSONString(transactionCompensateMsg);

		logger.info("Compensate->" + json);

		final String compensateKey = compensateDao.saveCompensateMsg(transactionCompensateMsg);

		//调整自动补偿机制，若开启了自动补偿，需要通知业务返回success，方可执行自动补偿
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String groupId = transactionCompensateMsg.getGroupId();
					JSONObject requestJson = new JSONObject();
					requestJson.put("action", "compensate");
					requestJson.put("groupId", groupId);
					requestJson.put("json", json);

					String url = configReader.getCompensateNotifyUrl();
					logger.error("Compensate Callback Address->" + url);
					String res = HttpUtils.postJson(url, requestJson.toJSONString());
					logger.error("Compensate Callback Result->" + res);
					if (configReader.isCompensateAuto()) {
						//自动补偿,是否自动执行补偿
						if (res.contains(SUCCESS) || res.contains(SUCCESS1)) {
							//自动补偿
							autoCompensate(compensateKey, transactionCompensateMsg);
						}
					}
				} catch (Exception e) {
					logger.error("Compensate Callback Fails->" + e.getMessage());
				}
			}
		});

		return StringUtils.isNotEmpty(compensateKey);


	}

	@Override
	public void autoCompensate(final String compensateKey, TransactionCompensateMsg transactionCompensateMsg) {
		final String json = JSON.toJSONString(transactionCompensateMsg);
		logger.info("Auto Compensate->" + json);
		//自动补偿业务执行...
		final int tryTime = configReader.getCompensateTryTime();
		boolean autoExecuteRes;
		try {
			int executeCount = 0;
			autoExecuteRes = executeCompensateMethod(json);
			logger.info("Automatic Compensate Result->" + autoExecuteRes + ",json->" + json);
			while (!autoExecuteRes) {
				logger.info("Compensate Failure, Entering Compensate Queue->" + autoExecuteRes + ",json->" + json);
				executeCount++;
				if (executeCount == 3) {
					autoExecuteRes = false;
					break;
				}
				try {
					Thread.sleep(tryTime * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				autoExecuteRes = executeCompensateMethod(json);
			}

			//执行成功删除数据
			if (autoExecuteRes) {
				compensateDao.deleteCompensateByKey(compensateKey);
			}

		} catch (Exception e) {
			logger.error("Auto Compensate Fails,msg:" + e.getLocalizedMessage());
			//推送数据给第三方通知
			autoExecuteRes = false;
		}

		//执行补偿以后通知给业务方
		String groupId = transactionCompensateMsg.getGroupId();
		JSONObject requestJson = new JSONObject();
		requestJson.put("action", "notify");
		requestJson.put("groupId", groupId);
		requestJson.put("resState", autoExecuteRes);

		String url = configReader.getCompensateNotifyUrl();
		logger.error("Compensate Result Callback Address->" + url);
		String res = HttpUtils.postJson(url, requestJson.toJSONString());
		logger.error("Compensate Result Callback Result->" + res);

	}


	@Override
	public List<ModelName> loadModelList() {
		List<String> keys = compensateDao.loadCompensateKeys();

		Map<String, Integer> models = new HashMap<>(16);

		for (String key : keys) {
			if (key.length() > 36) {
				String name = key.substring(11, key.length() - 25);
				int v = 1;
				if (models.containsKey(name)) {
					v = models.get(name) + 1;
				}
				models.put(name, v);
			}
		}
		List<ModelName> names = new ArrayList<>();

		for (String key : models.keySet()) {
			int v = models.get(key);
			ModelName modelName = new ModelName();
			modelName.setName(key);
			modelName.setCount(v);
			names.add(modelName);
		}
		return names;
	}

	@Override
	public List<String> loadCompensateTimes(String model) {
		return compensateDao.loadCompensateTimes(model);
	}

	@Override
	public List<TxModel> loadCompensateByModelAndTime(String path) {
		List<String> logs = compensateDao.loadCompensateByModelAndTime(path);

		List<TxModel> models = new ArrayList<>();
		for (String json : logs) {
			JSONObject jsonObject = JSON.parseObject(json);
			TxModel model = new TxModel();
			long currentTime = jsonObject.getLong("currentTime");
			model.setTime(DateUtil.formatDate(new Date(currentTime), DateUtil.FULL_DATE_TIME_FORMAT));
			model.setClassName(jsonObject.getString("className"));
			model.setMethod(jsonObject.getString("methodStr"));
			model.setExecuteTime(jsonObject.getInteger("time"));
			model.setBase64(Base64Utils.encode(json.getBytes()));
			model.setState(jsonObject.getInteger("state"));
			model.setOrder(currentTime);

			String groupId = jsonObject.getString("groupId");

			String key = path + ":" + groupId;
			model.setKey(key);

			models.add(model);
		}
		Collections.sort(models, new Comparator<TxModel>() {
			@Override
			public int compare(TxModel o1, TxModel o2) {
				if (o2.getOrder() > o1.getOrder()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return models;
	}

	@Override
	public boolean hasCompensate() {
		return compensateDao.hasCompensate();
	}

	@Override
	public boolean delCompensate(String path) {
		compensateDao.deleteCompensateByPath(path);
		return true;
	}

	@Override
	public void reloadCompensate(TxGroup txGroup) {
		TxGroup compensateGroup = getCompensateByGroupId(txGroup.getGroupId());
		if (compensateGroup != null) {

			if (compensateGroup.getList() != null && !compensateGroup.getList().isEmpty()) {
				//引用集合 iterator，方便匹配后剔除列表
				Iterator<TxInfo> iterator = Lists.newArrayList(compensateGroup.getList()).iterator();
				for (TxInfo txInfo : txGroup.getList()) {
					while (iterator.hasNext()) {
						TxInfo cinfo = iterator.next();
						if (cinfo.getModel().equals(txInfo.getModel()) && cinfo.getMethodStr().equals(txInfo.getMethodStr())) {
							//根据之前的数据补偿现在的事务
							int oldNotify = cinfo.getNotify();

							if (oldNotify == 1) {
								//本次回滚
								txInfo.setIsCommit(0);
							} else {
								//本次提交
								txInfo.setIsCommit(1);
							}
							//匹配后剔除列表
							iterator.remove();
							break;
						}
					}
				}
			} else {//当没有List数据只记录了补偿数据时，理解问仅发起方提交其他均回滚
				for (TxInfo txInfo : txGroup.getList()) {
					//本次回滚
					txInfo.setIsCommit(0);
				}
			}
		}
		logger.info("Compensate Loaded->" + JSON.toJSONString(txGroup));
	}

	@Override
	public TxGroup getCompensateByGroupId(String groupId) {
		String json = compensateDao.getCompensateByGroupId(groupId);
		if (json == null) {
			return null;
		}
		JSONObject jsonObject = JSON.parseObject(json);
		String txGroup = jsonObject.getString("txGroup");
		return JSON.parseObject(txGroup, TxGroup.class);
	}


	@Override
	@SneakyThrows
	public boolean executeCompensate(String path) {

		String json = compensateDao.getCompensate(path);
		if (json == null) {
			throw new ServiceException("no data existing");
		}

		boolean hasOk = executeCompensateMethod(json);
		if (hasOk) {
			// 删除本地补偿数据
			compensateDao.deleteCompensateByPath(path);

			return true;
		}
		return false;
	}

	@SneakyThrows
	private boolean executeCompensateMethod(String json) {
		JSONObject jsonObject = JSON.parseObject(json);

		String model = jsonObject.getString("model");

		int startError = jsonObject.getInteger("startError");

		ModelInfo modelInfo = ModelInfoManager.getInstance().getModelByModel(model);
		if (modelInfo == null) {
			throw new ServiceException("current model offline.");
		}

		String data = jsonObject.getString("data");

		String groupId = jsonObject.getString("groupId");

		String res = managerSenderService.sendCompensateMsg(modelInfo.getChannelName(), groupId, data, startError);

		logger.debug("executeCompensate->" + json + ",@@->" + res);

		return "1".equals(res);
	}
}
