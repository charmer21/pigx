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

package com.pig4cloud.pigx.manager.api.controller;

import com.lorne.core.framework.exception.ServiceException;
import com.pig4cloud.pigx.manager.api.service.ApiAdminService;
import com.pig4cloud.pigx.manager.api.service.ApiModelService;
import com.pig4cloud.pigx.manager.compensate.model.TxModel;
import com.pig4cloud.pigx.manager.model.ModelInfo;
import com.pig4cloud.pigx.manager.model.ModelName;
import com.pig4cloud.pigx.manager.model.TxState;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author LCN on 2017/7/1.
 * @author LCN
 * @author lengleng
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final ApiAdminService apiAdminService;
	private final ApiModelService apiModelService;


	@RequestMapping(value = "/onlines", method = RequestMethod.GET)
	public List<ModelInfo> onlines() {
		return apiModelService.onlines();
	}


	@RequestMapping(value = "/avueOnlines", method = RequestMethod.GET)
	public List<ModelInfo> avueOnlines() {
		return apiModelService.onlines();
	}


	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public TxState setting() {
		return apiAdminService.getState();
	}

	@RequestMapping(value = "/avueSetting", method = RequestMethod.GET)
	public List<Map<String, Object>> avueSetting() {
		return apiAdminService.getMapState();
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public String json() {
		return apiAdminService.loadNotifyJson();
	}

	@RequestMapping(value = "/modelList", method = RequestMethod.GET)
	public List<ModelName> modelList() {
		return apiAdminService.modelList();
	}

	@RequestMapping(value = "/modelTimes", method = RequestMethod.GET)
	public List<String> modelTimes(@RequestParam("model") String model) {
		return apiAdminService.modelTimes(model);
	}


	@RequestMapping(value = "/modelInfos", method = RequestMethod.GET)
	public List<TxModel> modelInfos(@RequestParam("path") String path) {
		return apiAdminService.modelInfos(path);
	}

	@SneakyThrows
	@RequestMapping(value = "/compensate", method = RequestMethod.GET)
	public boolean compensate(@RequestParam("path") String path) {
		return apiAdminService.compensate(path);
	}

	@SneakyThrows
	@RequestMapping(value = "/delCompensate", method = RequestMethod.GET)
	public boolean delCompensate(@RequestParam("path") String path) {
		return apiAdminService.delCompensate(path);
	}

	@SneakyThrows
	@RequestMapping(value = "/hasCompensate", method = RequestMethod.GET)
	public boolean hasCompensate() {
		return apiAdminService.hasCompensate();
	}
}
