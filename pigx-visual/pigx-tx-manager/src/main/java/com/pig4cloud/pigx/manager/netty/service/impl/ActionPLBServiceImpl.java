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

package com.pig4cloud.pigx.manager.netty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pigx.manager.manager.service.LoadBalanceService;
import com.pig4cloud.pigx.manager.model.LoadBalanceInfo;
import com.pig4cloud.pigx.manager.netty.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加负载模块信息
 * @author LCN on 2017/11/11
 */
@Service(value = "plb")
public class ActionPLBServiceImpl implements IActionService {


	@Autowired
	private LoadBalanceService loadBalanceService;


	@Override
	public String execute(String channelAddress, String key, JSONObject params) {

		String groupId = params.getString("g");
		String k = params.getString("k");
		String data = params.getString("d");

		LoadBalanceInfo loadBalanceInfo = new LoadBalanceInfo();
		loadBalanceInfo.setData(data);
		loadBalanceInfo.setKey(k);
		loadBalanceInfo.setGroupId(groupId);
		boolean ok = loadBalanceService.put(loadBalanceInfo);

		return ok ? "1" : "0";
	}
}
