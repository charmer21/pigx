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

package com.pig4cloud.pigx.common.job.jobinit;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.pig4cloud.pigx.common.job.properties.ElasticJobProperties;

import java.util.Map;


/**
 * @author lengleng
 * @date 2018/7/24
 * 脚本任务初始
 */
public class ScriptJobInitialization extends AbstractJobInitialization {

	private Map<String, ElasticJobProperties.ScriptConfiguration> scriptConfigurationMap;

	public ScriptJobInitialization(final Map<String, ElasticJobProperties.ScriptConfiguration> scriptConfigurationMap) {
		this.scriptConfigurationMap = scriptConfigurationMap;
	}

	public void init() {
		for (String jobName : scriptConfigurationMap.keySet()) {
			ElasticJobProperties.ScriptConfiguration configuration = scriptConfigurationMap.get(jobName);
			initJob(jobName, configuration.getJobType(), configuration);
		}
	}

	@Override
	public JobTypeConfiguration getJobTypeConfiguration(String jobName, JobCoreConfiguration jobCoreConfiguration) {
		ElasticJobProperties.ScriptConfiguration configuration = scriptConfigurationMap.get(jobName);
		return new ScriptJobConfiguration(jobCoreConfiguration, configuration.getScriptCommandLine());
	}
}
