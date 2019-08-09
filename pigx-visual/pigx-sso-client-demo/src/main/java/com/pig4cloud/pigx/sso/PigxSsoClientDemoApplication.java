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

package com.pig4cloud.pigx.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author lengleng
 * @date 2018年11月15日17:06:26
 * <p>
 * 单点登录客户端
 * 1. 启动实例访问:http://localhost:4040/sso1/ 提示登录，然后获取到用户信息
 * 2. 再启动一个实例： http://localhost:4041/sso1/ 不需要登录即可获取当前用户信息
 */
@EnableOAuth2Sso
@SpringCloudApplication
public class PigxSsoClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigxSsoClientDemoApplication.class, args);
	}

}
