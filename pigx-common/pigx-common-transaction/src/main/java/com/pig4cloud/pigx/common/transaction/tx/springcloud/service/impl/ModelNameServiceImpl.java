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

package com.pig4cloud.pigx.common.transaction.tx.springcloud.service.impl;

import com.codingapi.tx.listener.service.ModelNameService;
import com.lorne.core.framework.utils.encode.MD5Util;
import com.pig4cloud.pigx.common.transaction.tx.springcloud.listener.ServerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *@author LCN on 2017/7/12.
 *
 * @author LCN
 * @since 4.1.0
 */
@Service
@Configuration
public class ModelNameServiceImpl implements ModelNameService {

	@Value("${spring.application.name}")
	private String modelName;

	@Autowired
	private ServerListener serverListener;


	private String host = null;

	@Override
	public String getModelName() {
		return modelName;
	}

	private String getIp() {
		if (host == null) {
			try {
				host = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return host;
	}

	private int getPort() {
		int port = serverListener.getPort();
		int count = 0;
		while (port == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			port = serverListener.getPort();
			count++;

			if (count == 2000) {
				throw new RuntimeException("get server port error.");
			}
		}

		return port;
	}

	@Override
	public String getUniqueKey() {
		String address = getIp() + getPort();
		return MD5Util.md5(address.getBytes());
	}


	@Override
	public String getIpAddress() {
		String address = getIp() + ":" + getPort();
		return address;
	}
}
