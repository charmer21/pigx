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

package com.pig4cloud.pigx.manager.api.controller;

import com.pig4cloud.pigx.manager.api.service.ApiTxManagerService;
import com.pig4cloud.pigx.manager.model.TxServer;
import com.pig4cloud.pigx.manager.model.TxState;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *@author LCN on 2017/7/1.
 *
 * @author LCN
 * @author lengleng
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tx/manager")
public class TxManagerController {
	private final ApiTxManagerService apiTxManagerService;

	@RequestMapping("/getServer")
	public TxServer getServer() {
		return apiTxManagerService.getServer();
	}


	@RequestMapping("/cleanNotifyTransaction")
	public int cleanNotifyTransaction(@RequestParam("groupId") String groupId, @RequestParam("taskId") String taskId) {
		return apiTxManagerService.cleanNotifyTransaction(groupId, taskId);
	}


	@RequestMapping("/sendMsg")
	public String sendMsg(@RequestParam("msg") String msg, @RequestParam("model") String model) {
		return apiTxManagerService.sendMsg(model, msg);
	}


	@RequestMapping("/sendCompensateMsg")
	public boolean sendCompensateMsg(@RequestParam("model") String model, @RequestParam("uniqueKey") String uniqueKey,
									 @RequestParam("currentTime") long currentTime,
									 @RequestParam("groupId") String groupId, @RequestParam("className") String className,
									 @RequestParam("time") int time, @RequestParam("data") String data,
									 @RequestParam("methodStr") String methodStr, @RequestParam("address") String address,
									 @RequestParam("startError") int startError) {
		return apiTxManagerService.sendCompensateMsg(currentTime, groupId, model, address, uniqueKey, className, methodStr, data, time, startError);
	}


	@RequestMapping("/state")
	public TxState state() {
		return apiTxManagerService.getState();
	}
}
