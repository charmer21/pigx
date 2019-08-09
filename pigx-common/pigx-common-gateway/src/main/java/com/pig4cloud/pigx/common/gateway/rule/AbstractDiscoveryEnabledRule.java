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

package com.pig4cloud.pigx.common.gateway.rule;

import com.google.common.base.Optional;
import com.netflix.loadbalancer.*;
import com.pig4cloud.pigx.common.gateway.predicate.AbstractDiscoveryEnabledPredicate;
import org.springframework.util.Assert;

import java.util.List;

/**
 * ribbon 路由规则
 *
 * @author L.cm
 */
public abstract class AbstractDiscoveryEnabledRule extends PredicateBasedRule {
	private final CompositePredicate predicate;

	public AbstractDiscoveryEnabledRule(AbstractDiscoveryEnabledPredicate discoveryEnabledPredicate) {
		Assert.notNull(discoveryEnabledPredicate, "Parameter 'discoveryEnabledPredicate' can't be null");
		this.predicate = createCompositePredicate(discoveryEnabledPredicate, new AvailabilityPredicate(this, null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractServerPredicate getPredicate() {
		return predicate;
	}

	@Override
	public Server choose(Object key) {
		ILoadBalancer lb = getLoadBalancer();

		List<Server> allServers = lb.getAllServers();
		// 过滤服务列表
		List<Server> serverList = filterServers(allServers);

		Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(serverList, key);
		if (server.isPresent()) {
			return server.get();
		} else {
			return null;
		}
	}

	/**
	 * 过滤服务
	 *
	 * @param serverList 服务列表
	 * @return 服务列表
	 */
	public abstract List<Server> filterServers(List<Server> serverList);

	private CompositePredicate createCompositePredicate(AbstractDiscoveryEnabledPredicate discoveryEnabledPredicate, AvailabilityPredicate availabilityPredicate) {
		return CompositePredicate.withPredicates(discoveryEnabledPredicate, availabilityPredicate)
				.build();
	}
}
