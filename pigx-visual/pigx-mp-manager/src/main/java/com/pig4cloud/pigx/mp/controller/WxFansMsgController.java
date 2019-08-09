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
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.mp.entity.WxFansMsg;
import com.pig4cloud.pigx.mp.service.WxFansMsgService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 微信粉丝消息管理
 *
 * @author lengleng
 * @date 2019-03-27 20:45:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxfansmsg")
public class WxFansMsgController {

	private final WxFansMsgService wxFansMsgService;

	/**
	 * 分页查询
	 *
	 * @param page      分页对象
	 * @param wxFansMsg 消息
	 * @return
	 */
	@GetMapping("/page")
	public R getWxFansMsgPage(Page page, WxFansMsg wxFansMsg) {
		wxFansMsg.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(wxFansMsgService.page(page, Wrappers.query(wxFansMsg)));
	}


	/**
	 * 通过id查询消息
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(wxFansMsgService.getById(id));
	}

	/**
	 * 新增消息
	 *
	 * @param wxFansMsg 消息
	 * @return R
	 */
	@SysLog("新增消息")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('mp_wxfansmsg_add')")
	public R save(@RequestBody WxFansMsg wxFansMsg) {
		wxFansMsg.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(wxFansMsgService.save(wxFansMsg));
	}

	/**
	 * 修改消息
	 *
	 * @param wxFansMsg 消息
	 * @return R
	 */
	@SysLog("修改消息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('mp_wxfansmsg_edit')")
	public R updateById(@RequestBody WxFansMsg wxFansMsg) {
		wxFansMsg.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(wxFansMsgService.updateById(wxFansMsg));
	}

	/**
	 * 通过id删除消息
	 *
	 * @param id id
	 * @return R
	 */
	@SysLog("删除消息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('mp_wxfansmsg_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(wxFansMsgService.removeById(id));
	}

}
