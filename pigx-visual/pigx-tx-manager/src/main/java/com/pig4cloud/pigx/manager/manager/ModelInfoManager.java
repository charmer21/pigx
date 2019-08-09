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

package com.pig4cloud.pigx.manager.manager;

import com.pig4cloud.pigx.manager.model.ModelInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author LCN on 2017/11/13
 */
public class ModelInfoManager {


	private static ModelInfoManager manager = null;
	private List<ModelInfo> modelInfos = new CopyOnWriteArrayList<ModelInfo>();

	public static ModelInfoManager getInstance() {
		if (manager == null) {
			synchronized (ModelInfoManager.class) {
				if (manager == null) {
					manager = new ModelInfoManager();
				}
			}
		}
		return manager;
	}

	public void removeModelInfo(String channelName) {
		for (ModelInfo modelInfo : modelInfos) {
			if (channelName.equalsIgnoreCase(modelInfo.getChannelName())) {
				modelInfos.remove(modelInfo);
			}
		}
	}


	public void addModelInfo(ModelInfo minfo) {
		for (ModelInfo modelInfo : modelInfos) {
			if (minfo.getChannelName().equalsIgnoreCase(modelInfo.getChannelName())) {
				return;
			}

			if (minfo.getIpAddress().equalsIgnoreCase(modelInfo.getIpAddress())) {
				return;
			}
		}
		modelInfos.add(minfo);
	}

	public List<ModelInfo> getOnlines() {
		return modelInfos;
	}

	public ModelInfo getModelByChannelName(String channelName) {
		for (ModelInfo modelInfo : modelInfos) {
			if (channelName.equalsIgnoreCase(modelInfo.getChannelName())) {
				return modelInfo;
			}
		}
		return null;
	}

	public ModelInfo getModelByModel(String model) {
		for (ModelInfo modelInfo : modelInfos) {
			if (model.equalsIgnoreCase(modelInfo.getModel())) {
				return modelInfo;
			}
		}
		return null;
	}
}
