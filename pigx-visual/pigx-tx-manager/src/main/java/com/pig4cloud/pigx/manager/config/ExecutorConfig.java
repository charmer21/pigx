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

package com.pig4cloud.pigx.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lengleng
 * @date 2019-03-07
 * <p>
 * LCN线程池配置
 */
@Configuration
public class ExecutorConfig {
	private static final String LCN_SERVICE = "lcn-service-";

	@Bean
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//配置核心线程数
		executor.setCorePoolSize(10);
		//配置最大线程数
		executor.setMaxPoolSize(50);
		//配置队列大小
		executor.setQueueCapacity(99999);
		//配置线程池中的线程的名称前缀
		executor.setThreadNamePrefix(LCN_SERVICE);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		//执行初始化
		executor.initialize();
		return executor;
	}
}
