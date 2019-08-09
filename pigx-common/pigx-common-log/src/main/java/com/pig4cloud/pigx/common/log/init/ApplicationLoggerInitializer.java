package com.pig4cloud.pigx.common.log.init;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lengleng
 * @date 2019-05-22
 * <p>
 * 初始化日志路径
 */
public class ApplicationLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();

		String appName = environment.getProperty("spring.application.name");

		String logBase = environment.getProperty("LOGGING_PATH", "logs");
		// spring boot admin 直接加载日志
		System.setProperty("logging.file", String.format("%s/%s/debug.log", logBase, appName));
	}
}
