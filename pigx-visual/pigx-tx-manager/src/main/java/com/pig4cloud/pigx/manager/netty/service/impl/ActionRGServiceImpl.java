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
import com.pig4cloud.pigx.manager.manager.service.TxManagerService;
import com.pig4cloud.pigx.manager.netty.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 强制回滚事务组
 * @author LCN on 2017/11/11
 */
@Service(value = "rg")
public class ActionRGServiceImpl implements IActionService {


	@Autowired
	private TxManagerService txManagerService;

	@Override
	public String execute(String channelAddress, String key, JSONObject params) {
		String res = "";
		String groupId = params.getString("g");
		boolean bs = txManagerService.rollbackTransactionGroup(groupId);
		res = bs ? "1" : "0";
		return res;
	}
}
