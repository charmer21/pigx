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

package com.pig4cloud.pigx.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.api.entity.Test;
import com.pig4cloud.pigx.admin.service.SysLogService;
import com.pig4cloud.pigx.admin.service.TestService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 测试表
 *
 * @author pigx code generator
 * @date 2019-07-31 14:10:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/test" )
@Api(value = "test", tags = "测试表管理")
public class TestController {

    private final TestService testService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param test 测试表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTestPage(Page page, Test test) {
        return R.ok(testService.page(page, Wrappers.query(test)));
    }


    /**
     * 通过id查询测试表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(testService.getById(id));
    }

    /**
     * 新增测试表
     * @param test 测试表
     * @return R
     */
    @ApiOperation(value = "新增测试表", notes = "新增测试表")
    @SysLog("新增测试表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('generator_test_add')" )
    public R save(@RequestBody Test test) {
        return R.ok(testService.save(test));
    }

    /**
     * 修改测试表
     * @param test 测试表
     * @return R
     */
    @ApiOperation(value = "修改测试表", notes = "修改测试表")
    @SysLog("修改测试表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('generator_test_edit')" )
    public R updateById(@RequestBody Test test) {
        return R.ok(testService.updateById(test));
    }

    /**
     * 通过id删除测试表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除测试表", notes = "通过id删除测试表")
    @SysLog("通过id删除测试表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('generator_test_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(testService.removeById(id));
    }

}
