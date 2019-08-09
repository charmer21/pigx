package com.pig4cloud.pigx.daemon.elastic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 *
 *
 * @author lengleng
 * @date 2018-08-03 22:15:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_status_trace_log")
public class StatusTraceLog extends Model<StatusTraceLog> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *
	 */
	private String jobName;
	/**
	 *
	 */
	private String originalTaskId;
	/**
	 *
	 */
	private String taskId;
	/**
	 *
	 */
	private String slaveId;
	/**
	 *
	 */
	private String source;
	/**
	 *
	 */
	private String executionType;
	/**
	 *
	 */
	private String shardingItem;
	/**
	 *
	 */
	private String state;
	/**
	 *
	 */
	private String message;
	/**
	 *
	 */
	private LocalDateTime creationTime;

}
