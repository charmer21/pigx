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

package com.pig4cloud.pigx.monitor.support;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author lengleng
 * @date 2018/11/12
 * <p>
 * redis store event
 * default 100
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RedisEventStore extends InMemoryEventStore {
	private final RedisTemplate redisTemplate;

	@Override
	public Mono<Void> append(List<InstanceEvent> events) {
		events.forEach(event -> {
			String key = event.getInstance().getValue() + StrUtil.UNDERLINE + event.getTimestamp().toString();
			log.info("保存实例事件的KEY：{},EVENT: {}", key, event.getType());
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(InstanceEvent.class));
			redisTemplate.opsForHash().put(CacheConstants.EVENT_KEY, key, event);
		});
		return super.append(events);
	}
}
