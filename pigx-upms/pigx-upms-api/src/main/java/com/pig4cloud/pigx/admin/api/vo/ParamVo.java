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

package com.pig4cloud.pigx.admin.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: Lucky
 * @date: 2019/04/28
 * <p>
 * 公共参数
 */
@Data
@ApiModel(value = "公共参数展示对象")
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 公共参数键
     */
    @ApiModelProperty(value = "公共参数键")
    private String publicKey;
    /**
     * 公共参数值
     */
    @ApiModelProperty(value = "公共参数值")
    private String publicValue;
    /**
     * 公共参数名
     */
    @ApiModelProperty(value = "公共参数名称")
    private String publicName;
}