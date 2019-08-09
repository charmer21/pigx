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

package com.pig4cloud.pigx.manager.listener;

import com.pig4cloud.pigx.manager.listener.service.InitService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *@author LCN on 2017/7/1.
 */

@Component
public class ServerListener implements ServletContextListener {

	private WebApplicationContext springContext;


	private InitService initService;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils
			.getWebApplicationContext(event.getServletContext());
		initService = springContext.getBean(InitService.class);
		initService.start();
	}


	@Override
	public void contextDestroyed(ServletContextEvent event) {
		initService.close();
	}

}
