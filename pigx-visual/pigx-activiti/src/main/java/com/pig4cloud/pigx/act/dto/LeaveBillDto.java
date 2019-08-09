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

package com.pig4cloud.pigx.act.dto;

import com.pig4cloud.pigx.act.entity.LeaveBill;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author lengleng
 * @date 2018/9/28
 * LeaveBillDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LeaveBillDto extends LeaveBill {
	/**
	 * 任务ID
	 */
	private String taskId;

	/**
	 * 任务提交时间
	 */
	private Date time;

	/**
	 * 批准信息
	 */
	private String comment;

	/**
	 * 连线信息
	 */
	private List<String> flagList;

	private String taskFlag;
}
