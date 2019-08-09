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

package com.pig4cloud.pigx.common.transaction.tx.springcloud.interceptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * LCN 事务拦截器
 * @author LCN on 2018/1/5
 *
 * @author LCN
 * @since 4.1.0
 */

@Slf4j
@Aspect
@Component
public class TransactionAspect implements Ordered {

	@Autowired
	private TxManagerInterceptor txManagerInterceptor;

	@SneakyThrows
	@Around("@annotation(com.codingapi.tx.annotation.TxTransaction)")
	public Object transactionRunning(ProceedingJoinPoint point) {
		log.debug("annotation-TransactionRunning-start---->");
		Object obj = txManagerInterceptor.around(point);
		log.debug("annotation-TransactionRunning-end---->");
		return obj;
	}

	@SneakyThrows
	@Around("this(com.codingapi.tx.annotation.ITxTransaction) && execution( * *(..))")
	public Object around(ProceedingJoinPoint point) {
		log.debug("interface-ITransactionRunning-start---->");
		Object obj = txManagerInterceptor.around(point);
		log.debug("interface-ITransactionRunning-end---->");
		return obj;
	}


	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}


}
