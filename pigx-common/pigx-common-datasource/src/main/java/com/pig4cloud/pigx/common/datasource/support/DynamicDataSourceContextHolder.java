package com.pig4cloud.pigx.common.datasource.support;

import lombok.experimental.UtilityClass;

/**
 * @author lengleng
 * @date 2019-05-18
 * <p>
 * 根据当前线程来选择具体的数据源
 */
@UtilityClass
public class DynamicDataSourceContextHolder {
	private final ThreadLocal<Integer> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * 提供给AOP去设置当前的线程的数据源的信息
	 *
	 * @param dataSourceType
	 */
	public void setDataSourceType(Integer dataSourceType) {
		CONTEXT_HOLDER.set(dataSourceType);
	}

	/**
	 * 提供给AbstractRoutingDataSource的实现类，通过key选择数据源
	 *
	 * @return
	 */
	public Integer getDataSourceType() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * 使用默认的数据源
	 *
	 */
	public void clearDataSourceType() {
		CONTEXT_HOLDER.remove();
	}
}