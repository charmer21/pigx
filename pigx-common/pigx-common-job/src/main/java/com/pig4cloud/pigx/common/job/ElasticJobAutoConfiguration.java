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

package com.pig4cloud.pigx.common.job;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.pig4cloud.pigx.common.job.jobinit.DataflowJobInitialization;
import com.pig4cloud.pigx.common.job.jobinit.ScriptJobInitialization;
import com.pig4cloud.pigx.common.job.jobinit.SimpleJobInitialization;
import com.pig4cloud.pigx.common.job.properties.ElasticJobProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author lengleng
 * @date 2018/7/24
 * 任务初配置入口类
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(ElasticJobProperties.class)
public class ElasticJobAutoConfiguration {

	/**
	 * 默认注册中心
	 */
	public static final String DEFAULT_REGISTRY_CENTER_NAME = "elasticJobRegistryCenter";

	@Autowired
	private ElasticJobProperties elasticJobProperties;

	@Bean(name = DEFAULT_REGISTRY_CENTER_NAME, initMethod = "init")
	@ConditionalOnMissingBean
	public ZookeeperRegistryCenter elasticJobRegistryCenter() {
		ElasticJobProperties.ZkConfiguration regCenterProperties = elasticJobProperties.getZookeeper();
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(regCenterProperties.getServerLists(), regCenterProperties.getNamespace());
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(regCenterProperties.getBaseSleepTimeMilliseconds());
		zookeeperConfiguration.setConnectionTimeoutMilliseconds(regCenterProperties.getConnectionTimeoutMilliseconds());
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(regCenterProperties.getMaxSleepTimeMilliseconds());
		zookeeperConfiguration.setSessionTimeoutMilliseconds(regCenterProperties.getSessionTimeoutMilliseconds());
		zookeeperConfiguration.setMaxRetries(regCenterProperties.getMaxRetries());
		zookeeperConfiguration.setDigest(regCenterProperties.getDigest());
		return new ZookeeperRegistryCenter(zookeeperConfiguration);
	}

	@Bean(initMethod = "init")
	@ConditionalOnMissingBean
	@ConditionalOnBean(ZookeeperRegistryCenter.class)
	public SimpleJobInitialization simpleJobInitialization() {
		return new SimpleJobInitialization(elasticJobProperties.getSimples());
	}

	@Bean(initMethod = "init")
	@ConditionalOnMissingBean
	@ConditionalOnBean(ZookeeperRegistryCenter.class)
	public DataflowJobInitialization dataflowJobInitialization() {
		return new DataflowJobInitialization(elasticJobProperties.getDataflows());
	}

	@Bean(initMethod = "init")
	@ConditionalOnMissingBean
	@ConditionalOnBean(ZookeeperRegistryCenter.class)
	public ScriptJobInitialization scriptJobInitialization() {
		return new ScriptJobInitialization(elasticJobProperties.getScripts());
	}
}
