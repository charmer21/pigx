/*
 *    Copyright (c) 2018-2025, 云集汇通 All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the yunjihuitong.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 */

package com.pig4cloud.pigx.mp.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.enums.TicketType;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author lengleng
 * @date 2019/03/26
 * <p>
 * 微信SDK redis扩展
 */
public class WxMpInRedisConfigStorage extends WxMpInMemoryConfigStorage {

	private final static String ACCESS_TOKEN_KEY = "wechat:access_token_";

	private final static String JSAPI_TICKET_KEY = "wechat:jsapi_ticket_";

	private final static String CARDAPI_TICKET_KEY = "wechat:cardapi_ticket_";

	private final RedisTemplate<String, String> redisTemplate;

	private String accessTokenKey;

	private String jsapiTicketKey;

	private String cardapiTicketKey;

	public WxMpInRedisConfigStorage(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 每个公众号生成独有的存储key
	 *
	 * @param appId
	 */
	@Override
	public void setAppId(String appId) {
		super.setAppId(appId);
		this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
		this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appId);
		this.cardapiTicketKey = CARDAPI_TICKET_KEY.concat(appId);
	}

	@Override
	public String getAccessToken() {
		return redisTemplate.opsForValue().get(this.accessTokenKey);
	}

	@Override
	public boolean isAccessTokenExpired() {
		return redisTemplate.getExpire(accessTokenKey) < 2L;
	}

	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		redisTemplate.opsForValue().set(accessTokenKey, accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	@Override
	public void expireAccessToken() {
		redisTemplate.expire(this.accessTokenKey, 0, TimeUnit.SECONDS);
	}

	@Override
	public String getJsapiTicket() {
		return redisTemplate.opsForValue().get(this.jsapiTicketKey);
	}


	@Override
	public String getCardApiTicket() {
		return redisTemplate.opsForValue().get(cardapiTicketKey);
	}


	@Override
	public String getTicket(TicketType type) {
		return redisTemplate.opsForValue().get(getTicketRedisKey(type));
	}

	@Override
	public boolean isTicketExpired(TicketType type) {
		return redisTemplate.getExpire(this.getTicketRedisKey(type)) < 2;
	}

	@Override
	public synchronized void updateTicket(TicketType type, String jsapiTicket, int expiresInSeconds) {
		redisTemplate.opsForValue().set(getTicketRedisKey(type), jsapiTicket, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	@Override
	public void expireTicket(TicketType type) {
		redisTemplate.expire(getTicketRedisKey(type), 0, TimeUnit.SECONDS);
	}

	private String getTicketRedisKey(TicketType type) {
		return String.format("wechat:ticket:key:%s:%s", this.appId, type.getCode());
	}

}
