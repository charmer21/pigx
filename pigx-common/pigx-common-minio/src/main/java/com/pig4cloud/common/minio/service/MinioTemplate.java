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

package com.pig4cloud.common.minio.service;

import com.pig4cloud.common.minio.vo.MinioItem;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * minio 交互类
 *
 * @author lengleng
 */
@RequiredArgsConstructor
public class MinioTemplate implements InitializingBean {
	private final String endpoint;
	private final String accessKey;
	private final String secretKey;
	private MinioClient client;

	/**
	 * 创建bucket
	 *
	 * @param bucketName bucket名称
	 */
	@SneakyThrows
	public void createBucket(String bucketName) {
		if (!client.bucketExists(bucketName)) {
			client.makeBucket(bucketName);
		}
	}

	/**
	 * 获取全部bucket
	 * <p>
	 * https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
	 */
	@SneakyThrows
	public List<Bucket> getAllBuckets() {
		return client.listBuckets();
	}

	/**
	 * @param bucketName bucket名称
	 */
	@SneakyThrows
	public Optional<Bucket> getBucket(String bucketName) {
		return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
	}

	/**
	 * @param bucketName bucket名称
	 */
	@SneakyThrows
	public void removeBucket(String bucketName) {
		client.removeBucket(bucketName);
	}

	/**
	 * 根据文件前置查询文件
	 *
	 * @param bucketName bucket名称
	 * @param prefix     前缀
	 * @param recursive  是否递归查询
	 * @return MinioItem 列表
	 */
	@SneakyThrows
	public List<MinioItem> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
		List<MinioItem> objectList = new ArrayList<>();
		Iterable<Result<Item>> objectsIterator = client
				.listObjects(bucketName, prefix, recursive);

		while (objectsIterator.iterator().hasNext()) {
			objectList.add(new MinioItem(objectsIterator.iterator().next().get()));
		}
		return objectList;
	}

	/**
	 * 获取文件外链
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param expires    过期时间 <=7
	 * @return url
	 */
	@SneakyThrows
	public String getObjectURL(String bucketName, String objectName, Integer expires) {
		return client.presignedGetObject(bucketName, objectName, expires);
	}

	/**
	 * 获取文件
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @return 二进制流
	 */
	@SneakyThrows
	public InputStream getObject(String bucketName, String objectName) {
		return client.getObject(bucketName, objectName);
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param stream     文件流
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
	 */
	public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
		client.putObject(bucketName, objectName, stream, (long) stream.available(), null, null, "application/octet-stream");
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName  bucket名称
	 * @param objectName  文件名称
	 * @param stream      文件流
	 * @param size        大小
	 * @param contextType 类型
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
	 */
	public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
		client.putObject(bucketName, objectName, stream, size, null, null, contextType);
	}

	/**
	 * 获取文件信息
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
	 */
	public ObjectStat getObjectInfo(String bucketName, String objectName) throws Exception {
		return client.statObject(bucketName, objectName);
	}

	/**
	 * 删除文件
	 *
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
	 */
	public void removeObject(String bucketName, String objectName) throws Exception {
		client.removeObject(bucketName, objectName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(endpoint, "Minio url 为空");
		Assert.hasText(accessKey, "Minio accessKey为空");
		Assert.hasText(secretKey, "Minio secretKey为空");
		this.client = new MinioClient(endpoint, accessKey, secretKey);
	}

}
