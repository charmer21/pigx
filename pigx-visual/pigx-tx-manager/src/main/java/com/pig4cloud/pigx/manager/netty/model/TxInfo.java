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

package com.pig4cloud.pigx.manager.netty.model;

import com.alibaba.fastjson.JSONObject;
import com.lorne.core.framework.model.JsonModel;
import com.pig4cloud.pigx.manager.model.ChannelSender;

/**
 *@author LCN on 2017/6/7.
 */
public class TxInfo extends JsonModel {

	/**
	 * 任务唯一标示
	 */
	private String kid;

	/**
	 * 模块管道名称（netty管道名称）
	 */
	private String channelAddress;

	/**
	 * 是否通知成功
	 */
	private int notify;

	/**
	 * 0 不组合
	 * 1 组合
	 */
	private int isGroup;

	/**
	 * tm识别标示
	 */
	private String address;

	/**
	 * tx识别标示
	 */
	private String uniqueKey;


	/**
	 * 管道发送数据
	 */
	private ChannelSender channel;


	/**
	 * 业务方法名称
	 */
	private String methodStr;

	/**
	 * 模块名称
	 */
	private String model;

	/**
	 * 模块地址
	 */
	private String modelIpAddress;

	/**
	 * 是否提交（临时数据）
	 */
	private int isCommit;

	public int getIsCommit() {
		return isCommit;
	}

	public void setIsCommit(int isCommit) {
		this.isCommit = isCommit;
	}

	public String getMethodStr() {
		return methodStr;
	}

	public void setMethodStr(String methodStr) {
		this.methodStr = methodStr;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelIpAddress() {
		return modelIpAddress;
	}

	public void setModelIpAddress(String modelIpAddress) {
		this.modelIpAddress = modelIpAddress;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public ChannelSender getChannel() {
		return channel;
	}

	public void setChannel(ChannelSender channel) {
		this.channel = channel;
	}

	public String getChannelAddress() {
		return channelAddress;
	}

	public void setChannelAddress(String channelAddress) {
		this.channelAddress = channelAddress;
	}

	public int getNotify() {
		return notify;
	}

	public void setNotify(int notify) {
		this.notify = notify;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("kid", getKid());
		jsonObject.put("channelAddress", getChannelAddress());
		jsonObject.put("notify", getNotify());
		jsonObject.put("isGroup", getIsGroup());
		jsonObject.put("address", getAddress());
		jsonObject.put("uniqueKey", getUniqueKey());

		jsonObject.put("model", getModel());
		jsonObject.put("modelIpAddress", getModelIpAddress());
		jsonObject.put("methodStr", getMethodStr());

		return jsonObject.toString();
	}
}
