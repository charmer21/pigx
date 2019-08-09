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

package com.pig4cloud.pigx.common.gateway.predicate;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.SpringContextHolder;
import com.pig4cloud.pigx.common.gateway.support.PigxRibbonRuleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * 基于 Metadata version 的服务筛选
 *
 * @author L.cm
 * @author lengleng
 */
@Slf4j
public class GrayMetadataAwarePredicate extends AbstractDiscoveryEnabledPredicate {

	@Override
	protected boolean apply(NacosServer server, HttpHeaders headers) {
		PigxRibbonRuleProperties ribbonProperties = SpringContextHolder.getBean(PigxRibbonRuleProperties.class);

		if (!ribbonProperties.isGrayEnabled()) {
			log.debug("gray closed,GrayMetadataAwarePredicate return true");
			return true;
		}

		final Map<String, String> metadata = server.getMetadata();
		String version = metadata.get(CommonConstants.VERSION);
		// 判断Nacos服务是否有版本标签
		if (StrUtil.isBlank(version)) {
			log.debug("nacos server tag is blank ,GrayMetadataAwarePredicate return true");
			return true;
		}

		// 判断请求中是否有版本
		String target = headers.getFirst(CommonConstants.VERSION);
		if (StrUtil.isBlank(target)) {
			log.debug("request headers version is blank,GrayMetadataAwarePredicate return true");
			return true;
		}

		log.debug("请求版本:{} ,当前服务版本:{}", target, version);
		return target.equals(version);
	}

}
