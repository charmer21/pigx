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

package com.pig4cloud.pigx.daemon.elastic.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.daemon.elastic.entity.StatusTraceLog;
import com.pig4cloud.pigx.daemon.elastic.service.StatusTraceLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author lengleng
 * @date 2018-08-03 22:15:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/status-trace-log")
public class StatusTraceLogController {
	private final StatusTraceLogService statusTraceLogService;

	/**
	 * 任务轨迹处理简单分页查询
	 *
	 * @param page           分页对象
	 * @param statusTraceLog 任务轨迹处理
	 * @return
	 */
	@GetMapping("/page")
	public R getStatusTraceLogPage(Page<StatusTraceLog> page, StatusTraceLog statusTraceLog) {
		return R.ok(statusTraceLogService.page(page, Wrappers.query(statusTraceLog)));
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") String id) {
		return R.ok(statusTraceLogService.getById(id));
	}

	/**
	 * 保存
	 *
	 * @param statusTraceLog
	 * @return R
	 */
	@PostMapping
	public R save(@RequestBody StatusTraceLog statusTraceLog) {
		return R.ok(statusTraceLogService.save(statusTraceLog));
	}

	/**
	 * 修改
	 *
	 * @param statusTraceLog
	 * @return R
	 */
	@PutMapping
	public R update(@RequestBody StatusTraceLog statusTraceLog) {
		return R.ok(statusTraceLogService.updateById(statusTraceLog));
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable("id") String id) {
		return R.ok(statusTraceLogService.removeById(id));
	}

}
