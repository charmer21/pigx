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

package com.pig4cloud.pigx.common.job.properties;

import com.dangdang.ddframe.job.api.JobType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.pig4cloud.pigx.common.job.ElasticJobAutoConfiguration.DEFAULT_REGISTRY_CENTER_NAME;


/**
 * @author lengleng
 * @date 2018/7/24
 * 任务配置类
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.elasticjob")
public class ElasticJobProperties {

	/**
	 * 注册中心
	 */
	private ZkConfiguration zookeeper;

	/**
	 * 简单作业配置
	 */
	private Map<String, SimpleConfiguration> simples = new LinkedHashMap<>();

	/**
	 * 流式作业配置
	 */
	private Map<String, DataflowConfiguration> dataflows = new LinkedHashMap<>();

	/**
	 * 脚本作业配置
	 */
	private Map<String, ScriptConfiguration> scripts = new LinkedHashMap<>();

	@Getter
	@Setter
	public static class ZkConfiguration {

		/**
		 * 连接Zookeeper服务器的列表
		 * 包括IP地址和端口号
		 * 多个地址用逗号分隔
		 * 如: host1:2181,host2:2181
		 */
		private String serverLists;

		/**
		 * Zookeeper的命名空间
		 */
		private String namespace;

		/**
		 * 等待重试的间隔时间的初始值
		 * 单位：毫秒
		 */
		private int baseSleepTimeMilliseconds = 1000;

		/**
		 * 等待重试的间隔时间的最大值
		 * 单位：毫秒
		 */
		private int maxSleepTimeMilliseconds = 3000;

		/**
		 * 最大重试次数
		 */
		private int maxRetries = 3;

		/**
		 * 连接超时时间
		 * 单位：毫秒
		 */
		private int connectionTimeoutMilliseconds = 15000;

		/**
		 * 会话超时时间
		 * 单位：毫秒
		 */
		private int sessionTimeoutMilliseconds = 60000;

		/**
		 * 连接Zookeeper的权限令牌
		 * 缺省为不需要权限验
		 */
		private String digest;

	}

	@Getter
	@Setter
	public static class SimpleConfiguration extends JobConfiguration {
		/**
		 * 作业类型
		 */
		private final JobType jobType = JobType.SIMPLE;
	}

	@Getter
	@Setter
	public static class DataflowConfiguration extends JobConfiguration {
		/**
		 * 作业类型
		 */
		private final JobType jobType = JobType.DATAFLOW;
		/**
		 * 是否流式处理数据
		 * 如果流式处理数据, 则fetchData不返回空结果将持续执行作业
		 * 如果非流式处理数据, 则处理数据完成后作业结束
		 */
		private boolean streamingProcess = false;
	}

	@Getter
	@Setter
	public static class ScriptConfiguration extends JobConfiguration {
		/**
		 * 作业类型
		 */
		private final JobType jobType = JobType.SCRIPT;
		/**
		 * 脚本型作业执行命令行
		 */
		private String scriptCommandLine;
	}

	@Getter
	@Setter
	public static class JobConfiguration {

		/**
		 * 作业实现类，需实现ElasticJob接口
		 */
		private String jobClass;
		/**
		 * 注册中心Bean的引用，需引用reg:zookeeper的声明
		 */
		private String registryCenterRef = DEFAULT_REGISTRY_CENTER_NAME;
		/**
		 * cron表达式，用于控制作业触发时间
		 */
		private String cron;
		/**
		 * 作业分片总数
		 */
		private int shardingTotalCount = 1;
		/**
		 * 分片序列号和参数用等号分隔，多个键值对用逗号分隔
		 * 分片序列号从0开始，不可大于或等于作业分片总数
		 * 如：0=a,1=b,2=c
		 */
		private String shardingItemParameters = "0=A";
		/**
		 * 作业实例主键，同IP可运行实例主键不同, 但名称相同的多个作业实例
		 */
		private String jobInstanceId;
		/**
		 * 作业自定义参数
		 * 作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
		 * 例：每次获取的数据量、作业实例从数据库读取的主键等
		 */
		private String jobParameter;
		/**
		 * 监控作业运行时状态
		 * 每次作业执行时间和间隔时间均非常短的情况，建议不监控作业运行时状态以提升效率。
		 * 因为是瞬时状态，所以无必要监控。请用户自行增加数据堆积监控。并且不能保证数据重复选取，应在作业中实现幂等性。
		 * 每次作业执行时间和间隔时间均较长的情况，建议监控作业运行时状态，可保证数据不会重复选取。
		 */
		private boolean monitorExecution = true;
		/**
		 * 作业监控端口
		 * 建议配置作业监控端口, 方便开发者dump作业信息。
		 * 使用方法: echo “dump” | nc 127.0.0.1 9888
		 */
		private int monitorPort = -1;
		/**
		 * 最大允许的本机与注册中心的时间误差秒数
		 * 如果时间误差超过配置秒数则作业启动时将抛异常
		 * 配置为-1表示不校验时间误差
		 */
		private int maxTimeDiffSeconds = -1;
		/**
		 * 是否开启失效转移
		 */
		private boolean failover = false;
		/**
		 * 是否开启错过任务重新执行
		 */
		private boolean misfire = true;
		/**
		 * 作业分片策略实现类全路径
		 * 默认使用平均分配策略
		 * 详情参见：作业分片策略http://elasticjob.io/docs/elastic-job-lite/02-guide/job-sharding-strategy
		 */
		private String jobShardingStrategyClass;
		/**
		 * 作业描述信息
		 */
		private String description;
		/**
		 * 作业是否禁止启动
		 * 可用于部署作业时，先禁止启动，部署结束后统一启动
		 */
		private boolean disabled = false;
		/**
		 * 本地配置是否可覆盖注册中心配置
		 * 如果可覆盖，每次启动作业都以本地配置为准
		 */
		private boolean overwrite = true;
		/**
		 * 扩展异常处理类
		 */
		private String jobExceptionHandler;
		/**
		 * 扩展作业处理线程池类
		 */
		private String executorServiceHandler;
		/**
		 * 修复作业服务器不一致状态服务调度间隔时间，配置为小于1的任意值表示不执行修复
		 * 单位：分钟
		 */
		private int reconcileIntervalMinutes = 10;
		/**
		 * 作业事件追踪的数据源Bean引用
		 */
		private String eventTraceRdbDataSource;

		/**
		 * 监听器
		 */
		private Listener listener;

		@Getter
		@Setter
		public static class Listener {
			/**
			 * 每台作业节点均执行的监听
			 * 若作业处理作业服务器的文件，处理完成后删除文件，可考虑使用每个节点均执行清理任务。
			 * 此类型任务实现简单，且无需考虑全局分布式任务是否完成，请尽量使用此类型监听器。
			 *
			 * <p>注意：类必须继承com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener</p>
			 */
			String listenerClass;

			/**
			 * 分布式场景中仅单一节点执行的监听
			 * 若作业处理数据库数据，处理完成后只需一个节点完成数据清理任务即可。
			 * 此类型任务处理复杂，需同步分布式环境下作业的状态同步，提供了超时设置来避免作业不同步导致的死锁，请谨慎使用。
			 *
			 * <p>注意：类必须继承com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener</p>
			 */
			String distributedListenerClass;

			/**
			 * 最后一个作业执行前的执行方法的超时时间
			 * 单位：毫秒
			 */
			Long startedTimeoutMilliseconds = Long.MAX_VALUE;

			/**
			 * 最后一个作业执行后的执行方法的超时时间
			 * 单位：毫秒
			 */
			Long completedTimeoutMilliseconds = Long.MAX_VALUE;
		}
	}

}
