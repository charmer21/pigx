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

package com.pig4cloud.pigx.manager.netty.service.impl;

import com.pig4cloud.pigx.manager.config.ConfigReader;
import com.pig4cloud.pigx.manager.framework.utils.Constants;
import com.pig4cloud.pigx.manager.netty.handler.TxCoreServerHandler;
import com.pig4cloud.pigx.manager.netty.service.NettyServerService;
import com.pig4cloud.pigx.manager.netty.service.NettyService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author LCN on 2017/6/30.
 */
@Service
public class NettyServerServiceImpl implements NettyServerService, DisposableBean {
	private Logger logger = LoggerFactory.getLogger(NettyServerServiceImpl.class);
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private TxCoreServerHandler txCoreServerHandler;
	@Autowired
	private NettyService nettyService;
	@Autowired
	private Executor threadPool;
	@Autowired
	private ConfigReader configReader;


	@Override
	public void start() {
		final int heartTime = configReader.getTransactionNettyHeartTime() + 10;
		txCoreServerHandler = new TxCoreServerHandler(threadPool, nettyService);
		bossGroup = new NioEventLoopGroup(50);
		workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ch.pipeline().addLast("timeout", new IdleStateHandler(heartTime, heartTime, heartTime, TimeUnit.SECONDS));

							ch.pipeline().addLast(new LengthFieldPrepender(4, false));
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

							ch.pipeline().addLast(txCoreServerHandler);
						}
					});

			// Start the server.
			b.bind(Constants.socketPort);
			logger.info("Socket started on port(s): " + Constants.socketPort + " (socket)");

		} catch (Exception e) {
			// Shut down all event loops to terminate all threads.
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}

	}

	@Override
	public void destroy() {
		close();
	}
}
