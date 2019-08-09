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
package com.pig4cloud.pigx.codegen.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.codegen.entity.SysDatasourceConf;
import com.pig4cloud.pigx.codegen.service.SysDatasourceConfService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 数据源管理
 *
 * @author pigx code generator
 * @date 2019-03-31 16:00:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dsconf")
public class SysDsConfController {
	private final SysDatasourceConfService sysDatasourceConfService;

	/**
	 * 分页查询
	 *
	 * @param page              分页对象
	 * @param sysDatasourceConf 数据源表
	 * @return
	 */
	@GetMapping("/page")
	public R getSysDatasourceConfPage(Page page, SysDatasourceConf sysDatasourceConf) {
		return R.ok(sysDatasourceConfService.page(page, Wrappers.query(sysDatasourceConf)));
	}

	/**
	 * 查询全部数据源
	 *
	 * @return
	 */
	@GetMapping("/list")
	public R list() {
		return R.ok(sysDatasourceConfService.list());
	}


	/**
	 * 通过id查询数据源表
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(sysDatasourceConfService.getById(id));
	}

	/**
	 * 新增数据源表
	 *
	 * @param sysDatasourceConf 数据源表
	 * @return R
	 */
	@SysLog("新增数据源表")
	@PostMapping
	public R save(@RequestBody SysDatasourceConf sysDatasourceConf) {
		return R.ok(sysDatasourceConfService.saveDsByEnc(sysDatasourceConf));
	}

	/**
	 * 修改数据源表
	 *
	 * @param sysDatasourceConf 数据源表
	 * @return R
	 */
	@SysLog("修改数据源表")
	@PutMapping
	public R updateById(@RequestBody SysDatasourceConf sysDatasourceConf) {
		return R.ok(sysDatasourceConfService.updateDsByEnc(sysDatasourceConf));
	}

	/**
	 * 通过id删除数据源表
	 *
	 * @param id id
	 * @return R
	 */
	@SysLog("删除数据源表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return R.ok(sysDatasourceConfService.removeById(id));
	}

}
