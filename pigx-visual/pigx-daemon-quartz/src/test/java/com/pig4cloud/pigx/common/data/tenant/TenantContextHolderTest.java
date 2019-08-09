package com.pig4cloud.pigx.common.data.tenant;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lengleng
 * @date 2019-06-18
 */
public class TenantContextHolderTest {

	public static void main(String[] args) {
		TenantContextHolder.setTenantId(10000);
		System.out.println(TenantContextHolder.getTenantId());

		ThreadUtil.execAsync(() -> {
			System.out.println(TenantContextHolder.getTenantId());
		});
	}
}