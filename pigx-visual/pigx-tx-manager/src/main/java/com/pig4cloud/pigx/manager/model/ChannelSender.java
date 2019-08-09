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

package com.pig4cloud.pigx.manager.model;

import com.lorne.core.framework.utils.http.HttpUtils;
import com.lorne.core.framework.utils.task.IBack;
import com.lorne.core.framework.utils.task.Task;
import com.pig4cloud.pigx.manager.framework.utils.SocketUtils;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;

/**
 * @author LCN on 2017/8/7
 */
public class ChannelSender {


	private Channel channel;

	private String address;

	private String modelName;

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public void send(String msg) {
		if (channel != null) {
			SocketUtils.sendMsg(channel, msg);
		}

	}

	public void send(String msg, Task task) {
		if (channel != null) {
			SocketUtils.sendMsg(channel, msg);
		} else {
			String url = String.format("http://%s/tx/manager/sendMsg", address);
			final String res = HttpUtils.post(url, "msg=" + msg + "&model=" + modelName);
			if (StringUtils.isNotEmpty(res)) {
				if (task != null) {
					task.setBack(objs -> res);
					task.signalTask();
				}
			} else {
				if (task != null) {
					task.setBack(objs -> "-2");
					task.signalTask();
				}
			}
		}

	}
}
