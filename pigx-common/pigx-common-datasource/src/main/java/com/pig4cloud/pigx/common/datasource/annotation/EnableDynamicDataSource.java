package com.pig4cloud.pigx.common.datasource.annotation;

import com.pig4cloud.pigx.common.datasource.config.DynamicDataSourceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Lucky
 * @date 2019-05-18
 * <p>
 * 开启动态数据源
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DynamicDataSourceConfig.class})
public @interface EnableDynamicDataSource {
}
