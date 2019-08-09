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

package com.pig4cloud.pigx.manager.compensate.dao;

import com.pig4cloud.pigx.manager.compensate.model.TransactionCompensateMsg;

import java.util.List;

/**
 * @author LCN on 2017/11/11
 */
public interface CompensateDao {

	String saveCompensateMsg(TransactionCompensateMsg transactionCompensateMsg);

	List<String> loadCompensateKeys();

	List<String> loadCompensateTimes(String model);

	List<String> loadCompensateByModelAndTime(String path);

	String getCompensate(String key);

	String getCompensateByGroupId(String groupId);

	void deleteCompensateByPath(String path);

	void deleteCompensateByKey(String key);

	boolean hasCompensate();
}
