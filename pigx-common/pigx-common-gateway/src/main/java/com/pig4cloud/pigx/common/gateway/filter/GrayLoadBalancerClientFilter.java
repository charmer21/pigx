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

package com.pig4cloud.pigx.common.gateway.filter;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author Spencer Gibb
 * @author Tim Ysewyn
 */
public class GrayLoadBalancerClientFilter extends LoadBalancerClientFilter {
	private final RibbonLoadBalancerClient loadBalancer;

	public GrayLoadBalancerClientFilter(RibbonLoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
		super(loadBalancer, properties);
		this.loadBalancer = loadBalancer;
	}

	@Override
	protected ServiceInstance choose(ServerWebExchange exchange) {
		HttpHeaders headers = exchange.getRequest().getHeaders();
		return loadBalancer.choose(((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost(), headers);
	}

}
