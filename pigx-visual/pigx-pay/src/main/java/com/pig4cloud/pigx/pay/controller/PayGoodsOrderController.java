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

package com.pig4cloud.pigx.pay.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.pay.config.PayConfigParmaInitRunner;
import com.pig4cloud.pigx.pay.entity.PayGoodsOrder;
import com.pig4cloud.pigx.pay.service.PayGoodsOrderService;
import com.pig4cloud.pigx.pay.utils.PayConstants;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 商品
 *
 * @author lengleng
 * @date 2019-05-28 23:58:27
 */
@Controller
@AllArgsConstructor
@RequestMapping("/goods")
@Api(value = "goods", tags = "商品订单管理")
public class PayGoodsOrderController {

	private final PayGoodsOrderService payGoodsOrderService;


	/**
	 * 商品订单
	 *
	 * @param goods        商品
	 * @param modelAndView
	 * @return R
	 */
	@Inner(false)
	@GetMapping("/buy")
	@SysLog("购买商品")
	public ModelAndView buy(PayGoodsOrder goods, HttpServletRequest request, ModelAndView modelAndView) {
		String ua = request.getHeader(HttpHeaders.USER_AGENT);
		if (ua.contains(PayConstants.MICRO_MESSENGER)) {
			String appId = WxPayApiConfigKit.getWxPayApiConfig().getAppId();
			modelAndView.setViewName("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId +
					"&redirect_uri=http%3a%2f%2fadmin.pig4cloud.com%2fpay%2fgoods%2fwx%3famount%3d" + goods.getAmount() +
					"&response_type=code&scope=snsapi_base&state=" + appId);
			return modelAndView;
		}

		modelAndView.setViewName("pay");
		modelAndView.addAllObjects(payGoodsOrderService.buy(goods));
		return modelAndView;
	}

	/**
	 * oauth
	 *
	 * @param goods        商品信息
	 * @param state        appid
	 * @param code         回调code
	 * @param modelAndView
	 * @return
	 * @throws WxErrorException
	 */
	@Inner(false)
	@SneakyThrows
	@GetMapping("/wx")
	public ModelAndView wx(PayGoodsOrder goods, String state, String code, ModelAndView modelAndView) {
		WxMpService wxMpService = PayConfigParmaInitRunner.mpServiceMap.get(state);
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		goods.setUserId(wxMpOAuth2AccessToken.getOpenId());
		goods.setAmount(goods.getAmount());
		modelAndView.setViewName("pay");
		modelAndView.addAllObjects(payGoodsOrderService.buy(goods));
		return modelAndView;
	}

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param payGoodsOrder 商品订单表
	 * @return
	 */
	@ResponseBody
	@GetMapping("/page")
	public R getPayGoodsOrderPage(Page page, PayGoodsOrder payGoodsOrder) {
		return R.ok(payGoodsOrderService.page(page, Wrappers.query(payGoodsOrder)));
	}


	/**
	 * 通过id查询商品订单表
	 *
	 * @param goodsOrderId id
	 * @return R
	 */
	@ResponseBody
	@GetMapping("/{goodsOrderId}")
	public R getById(@PathVariable("goodsOrderId") Integer goodsOrderId) {
		return R.ok(payGoodsOrderService.getById(goodsOrderId));
	}

	/**
	 * 新增商品订单表
	 *
	 * @param payGoodsOrder 商品订单表
	 * @return R
	 */
	@SysLog("新增商品订单表")
	@PostMapping
	@ResponseBody
	@PreAuthorize("@pms.hasPermission('generator_paygoodsorder_add')")
	public R save(@RequestBody PayGoodsOrder payGoodsOrder) {
		return R.ok(payGoodsOrderService.save(payGoodsOrder));
	}

	/**
	 * 修改商品订单表
	 *
	 * @param payGoodsOrder 商品订单表
	 * @return R
	 */
	@SysLog("修改商品订单表")
	@PutMapping
	@ResponseBody
	@PreAuthorize("@pms.hasPermission('generator_paygoodsorder_edit')")
	public R updateById(@RequestBody PayGoodsOrder payGoodsOrder) {
		return R.ok(payGoodsOrderService.updateById(payGoodsOrder));
	}

	/**
	 * 通过id删除商品订单表
	 *
	 * @param goodsOrderId id
	 * @return R
	 */
	@SysLog("删除商品订单表")
	@ResponseBody
	@DeleteMapping("/{goodsOrderId}")
	@PreAuthorize("@pms.hasPermission('generator_paygoodsorder_del')")
	public R removeById(@PathVariable Integer goodsOrderId) {
		return R.ok(payGoodsOrderService.removeById(goodsOrderId));
	}

}
