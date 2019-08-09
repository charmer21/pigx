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

package com.pig4cloud.pigx.common.transaction.tx.springcloud.listener;

import com.codingapi.tx.listener.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author LCN
 * @since 4.1.0
 */
@Component
public class ServerListener implements ApplicationListener<WebServerInitializedEvent> {

	private Logger logger = LoggerFactory.getLogger(ServerListener.class);

	private int serverPort;

	@Autowired
	private InitService initService;

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		logger.info("onApplicationEvent -> onApplicationEvent. " + event.getWebServer());
		this.serverPort = event.getWebServer().getPort();
		initService.start();
	}

	public int getPort() {
		return this.serverPort;
	}
}
