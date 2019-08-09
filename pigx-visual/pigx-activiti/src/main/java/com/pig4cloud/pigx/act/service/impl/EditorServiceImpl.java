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

package com.pig4cloud.pigx.act.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pigx.act.service.EditorService;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.activiti.editor.constants.ModelDataJsonConstants.*;

/**
 * @author lengleng
 * @date 2018/9/25
 */
@Slf4j
@Service
@AllArgsConstructor
public class EditorServiceImpl implements EditorService {
	private final RepositoryService repositoryService;
	private final ObjectMapper objectMapper;

	/**
	 * 获取设计器页面的json
	 *
	 * @return
	 */
	@Override
	public Object getStencilset() {
		InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
		try {
			return IOUtils.toString(stencilsetStream, StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error("Error while loading stencil set", e);
			throw new ActivitiException("Error while loading stencil set", e);
		}
	}

	/**
	 * 根据modelId获取model
	 *
	 * @param modelId
	 * @return
	 */
	@Override
	public Object getEditorJson(String modelId) {
		ObjectNode modelNode;
		Model model = repositoryService.getModel(modelId);
		if (model != null) {
			try {
				if (StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
				} else {
					modelNode = objectMapper.createObjectNode();
					modelNode.put(MODEL_NAME, model.getName());
				}
				byte[] source = repositoryService.getModelEditorSource(model.getId());
				modelNode.put(MODEL_ID, model.getId());
				ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(new String(source, StandardCharsets.UTF_8));
				modelNode.set("model", editorJsonNode);
				return modelNode;
			} catch (Exception e) {
				log.error("Error creating model JSON", e);
				throw new ActivitiException("Error creating model JSON", e);
			}
		}
		return null;
	}

	/**
	 * 保存model信息
	 *
	 * @param modelId
	 * @param name
	 * @param description
	 * @param jsonXml
	 * @param svgXml
	 */
	@Override
	public void saveModel(String modelId, String name, String description, String jsonXml, String svgXml) {
		try {
			Model model = repositoryService.getModel(modelId);
			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
			modelJson.put(MODEL_NAME, name);
			modelJson.put(MODEL_DESCRIPTION, description);
			model.setMetaInfo(modelJson.toString());
			model.setName(name);
			model.setTenantId(String.valueOf(TenantContextHolder.getTenantId()));

			repositoryService.saveModel(model);
			repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes(StandardCharsets.UTF_8));
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			final byte[] result = outStream.toByteArray();
			repositoryService.addModelEditorSourceExtra(model.getId(), result);
			outStream.close();
		} catch (Exception e) {
			log.error("Error saving model", e);
			throw new ActivitiException("Error saving model", e);
		}
	}
}
