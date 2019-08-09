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
package com.pig4cloud.pigx.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.mp.entity.WxFansMsgRes;

/**
 * 微信消息回复业务
 *
 * @author lengleng
 * @date 2019-03-27 20:45:48
 */
public interface WxFansMsgResService extends IService<WxFansMsgRes> {

	/**
	 * 保存并发送回复
	 *
	 * @param wxFansMsgRes
	 * @return
	 */
	R saveAndSend(WxFansMsgRes wxFansMsgRes);
}
