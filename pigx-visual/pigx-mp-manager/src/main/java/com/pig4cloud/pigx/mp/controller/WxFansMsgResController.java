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
package com.pig4cloud.pigx.mp.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.mp.entity.WxFansMsgRes;
import com.pig4cloud.pigx.mp.service.WxFansMsgResService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 微信粉丝消息回复管理
 *
 * @author lengleng
 * @date 2019-03-27 20:45:48
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxfansmsgres")
public class WxFansMsgResController {

	private final WxFansMsgResService wxFansMsgResService;

	/**
	 * 分页查询
	 *
	 * @param page         分页对象
	 * @param wxFansMsgRes 回复
	 * @return
	 */
	@GetMapping("/page")
	public R getWxFansMsgResPage(Page page, WxFansMsgRes wxFansMsgRes) {
		return R.ok(wxFansMsgResService.page(page, Wrappers.query(wxFansMsgRes)));
	}


	/**
	 * 通过id查询回复
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(wxFansMsgResService.getById(id));
	}

	/**
	 * 新增回复
	 *
	 * @param wxFansMsgRes 回复
	 * @return R
	 */
	@SysLog("新增回复")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('mp_wxfansmsgres_add')")
	public R save(@RequestBody WxFansMsgRes wxFansMsgRes) {
		return wxFansMsgResService.saveAndSend(wxFansMsgRes);
	}

	/**
	 * 修改回复
	 *
	 * @param wxFansMsgRes 回复
	 * @return R
	 */
	@SysLog("修改回复")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('mp_wxfansmsgres_edit')")
	public R updateById(@RequestBody WxFansMsgRes wxFansMsgRes) {
		return R.ok(wxFansMsgResService.updateById(wxFansMsgRes));
	}

	/**
	 * 通过id删除回复
	 *
	 * @param id id
	 * @return R
	 */
	@SysLog("删除回复")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('mp_wxfansmsgres_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(wxFansMsgResService.removeById(id));
	}

}
