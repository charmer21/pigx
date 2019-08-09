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
package com.pig4cloud.pigx.mp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.mp.config.WxMpConfiguration;
import com.pig4cloud.pigx.mp.entity.WxAccount;
import com.pig4cloud.pigx.mp.entity.WxAccountFans;
import com.pig4cloud.pigx.mp.mapper.WxAccountFansMapper;
import com.pig4cloud.pigx.mp.mapper.WxAccountMapper;
import com.pig4cloud.pigx.mp.service.WxAccountFansService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2019-03-26 22:08:08
 * <p>
 * 微信公众号粉丝
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxAccountFansServiceImpl extends ServiceImpl<WxAccountFansMapper, WxAccountFans> implements WxAccountFansService {
	private static final int SIZE = 100;
	private final WxAccountMapper wxAccountMapper;

	/**
	 * 获取公众号粉丝，生产建议异步
	 *
	 * @param appId
	 * @return
	 */
	@Async
	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public Boolean syncAccountFans(String appId) {
		WxAccount wxAccount = wxAccountMapper
				.selectOne(Wrappers.<WxAccount>query().lambda()
						.eq(WxAccount::getAppid, appId));

		WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appId);
		WxMpUserService wxMpUserService = wxMpService.getUserService();
		fetchUser(null, wxAccount, wxMpUserService);
		return Boolean.TRUE;
	}

	/**
	 * 获取微信用户
	 *
	 * @param nextOpenid
	 * @param wxAccount
	 * @param wxMpUserService
	 * @throws WxErrorException
	 */
	private void fetchUser(String nextOpenid, WxAccount wxAccount, WxMpUserService wxMpUserService) throws WxErrorException {
		WxMpUserList wxMpUserList = wxMpUserService.userList(nextOpenid);

		// openId 分组 每组 100个 openid
		CollUtil.split(wxMpUserList.getOpenids(), SIZE)
				.stream().filter(CollUtil::isNotEmpty)
				.forEach(openIdList -> {
					log.info("开始批量获取用户信息 {}", openIdList);
					List<WxAccountFans> wxAccountFansList = new ArrayList<>();
					try {
						wxMpUserService.userInfoList(openIdList).forEach(wxMpUser -> {
							WxAccountFans wxAccountFans = baseMapper
									.selectOne(Wrappers.<WxAccountFans>query().lambda()
											.eq(WxAccountFans::getOpenid, wxMpUser.getOpenId()));
							// 为空初始化一个新的对象
							if (wxAccountFans == null) {
								wxAccountFans = new WxAccountFans();
							}

							wxAccountFans.setOpenid(wxMpUser.getOpenId());
							wxAccountFans.setSubscribeStatus(wxMpUser.getSubscribe() ? "1" : "0");
							wxAccountFans.setSubscribeTime(LocalDateTime
									.ofInstant(Instant.ofEpochMilli(wxMpUser.getSubscribeTime() * 1000L)
											, ZoneId.systemDefault()));
							wxAccountFans.setNickname(wxMpUser.getNickname());
							wxAccountFans.setGender(String.valueOf(wxMpUser.getSex()));
							wxAccountFans.setLanguage(wxMpUser.getLanguage());
							wxAccountFans.setCountry(wxMpUser.getCountry());
							wxAccountFans.setProvince(wxMpUser.getProvince());
							wxAccountFans.setCity(wxMpUser.getCity());
							wxAccountFans.setHeadimgUrl(wxMpUser.getHeadImgUrl());
							wxAccountFans.setRemark(wxMpUser.getRemark());
							wxAccountFans.setWxAccountId(wxAccount.getId());
							wxAccountFans.setWxAccountAppid(wxAccount.getAppid());
							wxAccountFans.setWxAccountName(wxAccount.getName());
							wxAccountFansList.add(wxAccountFans);
						});
						this.saveOrUpdateBatch(wxAccountFansList);
						log.info("批量插入用户信息完成 {}", openIdList);
					} catch (WxErrorException e) {
						log.error("批量同步粉丝失败", e);
					}
				});

		// 如果nextOpenId 不为空，则继续获取
		if (StrUtil.isNotBlank(wxMpUserList.getNextOpenid())) {
			fetchUser(wxMpUserList.getNextOpenid(), wxAccount, wxMpUserService);
		}

		log.info("批量同步微信用户信息完成");
	}
}
