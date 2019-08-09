package com.pig4cloud.pigx.common.datasource.config;

import com.pig4cloud.pigx.common.datasource.support.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author lengleng
 * @date 2019-03-31
 * <p>
 * 动态数据源类
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 指定路由Key
	 *
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}
}