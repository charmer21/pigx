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

/**
 *@author LCN on 2017/7/1.
 */
public class TxServer {

	private String ip;
	private int port;
	private int heart;
	private int delay;
	private int compensateMaxWaitTime;

	public static TxServer format(TxState state) {
		TxServer txServer = new TxServer();
		txServer.setIp(state.getIp());
		txServer.setPort(state.getPort());
		txServer.setHeart(state.getTransactionNettyHeartTime());
		txServer.setDelay(state.getTransactionNettyDelayTime());
		txServer.setCompensateMaxWaitTime(state.getCompensateMaxWaitTime());
		return txServer;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}


	public int getCompensateMaxWaitTime() {
		return compensateMaxWaitTime;
	}


	public void setCompensateMaxWaitTime(int compensateMaxWaitTime) {
		this.compensateMaxWaitTime = compensateMaxWaitTime;
	}


}
