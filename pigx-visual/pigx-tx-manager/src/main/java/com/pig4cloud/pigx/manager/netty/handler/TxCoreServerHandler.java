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

package com.pig4cloud.pigx.manager.netty.handler;

/**
 * @author LCN on 2017/6/29.
 */

import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pigx.manager.framework.utils.SocketManager;
import com.pig4cloud.pigx.manager.framework.utils.SocketUtils;
import com.pig4cloud.pigx.manager.manager.ModelInfoManager;
import com.pig4cloud.pigx.manager.netty.service.IActionService;
import com.pig4cloud.pigx.manager.netty.service.NettyService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * Handles a server-side channel.
 *
 * @author lengleng
 */

@ChannelHandler.Sharable
public class TxCoreServerHandler extends ChannelInboundHandlerAdapter { // (1)

	private NettyService nettyService;


	private Logger logger = LoggerFactory.getLogger(TxCoreServerHandler.class);


	private Executor threadPool;


	public TxCoreServerHandler(Executor threadPool, NettyService nettyService) {
		this.threadPool = threadPool;
		this.nettyService = nettyService;
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		final String json = SocketUtils.getJson(msg);
		logger.debug("request->" + json);
		threadPool.execute(() -> service(json, ctx));
	}

	private void service(String json, ChannelHandlerContext ctx) {
		if (StringUtils.isNotEmpty(json)) {
			JSONObject jsonObject = JSONObject.parseObject(json);
			String action = jsonObject.getString("a");
			String key = jsonObject.getString("k");
			JSONObject params = JSONObject.parseObject(jsonObject.getString("p"));
			String channelAddress = ctx.channel().remoteAddress().toString();

			IActionService actionService = nettyService.getActionService(action);

			String res = actionService.execute(channelAddress, key, params);

			JSONObject resObj = new JSONObject();
			resObj.put("k", key);
			resObj.put("d", res);

			SocketUtils.sendMsg(ctx, resObj.toString());

		}
	}

	@Override
	@SneakyThrows
	public void channelRegistered(ChannelHandlerContext ctx) {

		//是否到达最大上线连接数
		if (SocketManager.getInstance().isAllowConnection()) {
			SocketManager.getInstance().addClient(ctx.channel());
		} else {
			ctx.close();
		}
		super.channelRegistered(ctx);
	}

	@Override
	@SneakyThrows
	public void channelUnregistered(ChannelHandlerContext ctx) {

		SocketManager.getInstance().removeClient(ctx.channel());
		String modelName = ctx.channel().remoteAddress().toString();
		SocketManager.getInstance().outLine(modelName);

		ModelInfoManager.getInstance().removeModelInfo(modelName);
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		//ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
		//心跳配置
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				ctx.close();
			}
		}
	}

}
