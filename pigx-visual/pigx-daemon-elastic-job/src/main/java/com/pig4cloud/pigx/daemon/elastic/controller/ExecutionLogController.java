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
import com.pig4cloud.pigx.daemon.elastic.entity.ExecutionLog;
import com.pig4cloud.pigx.daemon.elastic.service.ExecutionLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author lengleng
 * @date 2018-08-03 22:15:56
 */
@RestController
@RequestMapping("/execution-log")
@AllArgsConstructor
public class ExecutionLogController {

	private final ExecutionLogService executionLogService;

	/**
	 * 任务日志处理简单分页查询
	 *
	 * @param page         分页对象
	 * @param executionLog 任务日志处理
	 * @return
	 */
	@GetMapping("/page")
	public R getExecutionLogPage(Page page, ExecutionLog executionLog) {
		return R.ok(executionLogService.page(page, Wrappers.query(executionLog)));
	}


	/**
	 * 通过id查询一条记录
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") String id) {
		return R.ok(executionLogService.getById(id));
	}

	/**
	 * 保存
	 *
	 * @param executionLog
	 * @return R
	 */
	@PostMapping
	public R save(@RequestBody ExecutionLog executionLog) {
		return R.ok(executionLogService.save(executionLog));
	}

	/**
	 * 修改
	 *
	 * @param executionLog
	 * @return R
	 */
	@PutMapping
	public R update(@RequestBody ExecutionLog executionLog) {
		return R.ok(executionLogService.updateById(executionLog));
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable String id) {
		return R.ok(executionLogService.removeById(id));
	}

}
