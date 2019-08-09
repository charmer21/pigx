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

package com.pig4cloud.pigx.common.transaction.rewrite;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import com.pig4cloud.pigx.common.core.constant.ServiceNameConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;

/**
 * @author lengleng
 * @date 2018/8/19
 * 使用服务发现重写 Txmanager 获取规则
 */
@Slf4j
@Service
@AllArgsConstructor
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {
	private final LoadBalancerClient loadBalancerClient;

	@Override
	public String getTxUrl() {
		ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceNameConstants.TX_MANAGER);
		String host = serviceInstance.getHost();
		Integer port = serviceInstance.getPort();
		String url = String.format("http://%s:%s/tx/manager/", host, port);
		log.info("tm.manager.url -> {}", url);
		return url;
	}
}
