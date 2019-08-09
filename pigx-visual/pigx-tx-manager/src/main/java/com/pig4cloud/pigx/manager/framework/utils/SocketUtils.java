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

package com.pig4cloud.pigx.manager.framework.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.experimental.UtilityClass;

/**
 * @author LCN on 2017/7/6.
 */
@UtilityClass
public class SocketUtils {

	public String getJson(Object msg) {
		String json;
		try {
			ByteBuf buf = (ByteBuf) msg;
			byte[] bytes = new byte[buf.readableBytes()];
			buf.readBytes(bytes);
			json = new String(bytes);
		} finally {
			ReferenceCountUtil.release(msg);
		}
		return json;

	}

	public void sendMsg(ChannelHandlerContext ctx, String msg) {
		ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));
	}


	public void sendMsg(Channel ctx, String msg) {
		ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));
	}
}
