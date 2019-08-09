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

package com.pig4cloud.pigx.common.gateway.configuration;

import com.pig4cloud.pigx.common.gateway.filter.GrayLoadBalancerClientFilter;
import com.pig4cloud.pigx.common.gateway.rule.AbstractDiscoveryEnabledRule;
import com.pig4cloud.pigx.common.gateway.rule.MetadataAwareRule;
import com.pig4cloud.pigx.common.gateway.support.PigxRibbonRuleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Mica ribbon rule auto configuration.
 *
 * @author L.cm
 * @link https://github.com/lets-mica/mica
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(NacosServer.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@EnableConfigurationProperties(PigxRibbonRuleProperties.class)
@ConditionalOnProperty(value = "ribbon.rule.enabled", matchIfMissing = true)
public class PigxRibbonRuleAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AbstractDiscoveryEnabledRule metadataAwareRule() {
		return new MetadataAwareRule();
	}

	@Bean
	@ConditionalOnMissingBean(LoadBalancerClient.class)
	public LoadBalancerClient loadBalancerClient(SpringClientFactory springClientFactory) {
		return new RibbonLoadBalancerClient(springClientFactory);
	}

	@Bean
	@ConditionalOnMissingBean(LoadBalancerClientFilter.class)
	public LoadBalancerClientFilter loadBalancerClientFilter(RibbonLoadBalancerClient client
			, LoadBalancerProperties properties) {
		return new GrayLoadBalancerClientFilter(client, properties);
	}

}
