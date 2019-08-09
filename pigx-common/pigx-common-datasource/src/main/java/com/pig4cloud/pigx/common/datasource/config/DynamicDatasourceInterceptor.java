package com.pig4cloud.pigx.common.datasource.config;

import com.pig4cloud.pigx.common.datasource.support.DynamicDataSourceContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lengleng
 * @date 2019-05-18
 * <p>
 * 动态数据源拦截器
 */
@Slf4j
@Component
@AllArgsConstructor
public class DynamicDatasourceInterceptor implements HandlerInterceptor {

	/**
	 * TODO 根据上下文判断路由哪个数据库
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		DynamicDataSourceContextHolder.setDataSourceType(1);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								@Nullable Exception ex) {
		DynamicDataSourceContextHolder.clearDataSourceType();
	}

}
