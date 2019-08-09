package com.pig4cloud.pigx.daemon.elastic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 *
 * @author lengleng
 * @date 2018-08-03 22:15:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_execution_log")
public class ExecutionLog extends Model<ExecutionLog> {
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
	private String taskId;
	/**
	 *
	 */
	private String hostname;
	/**
	 *
	 */
	private String ip;
	/**
	 *
	 */
	private Integer shardingItem;
	/**
	 *
	 */
	private String executionSource;
	/**
	 *
	 */
	private String failureCause;
	/**
	 *
	 */
	private Integer isSuccess;
	/**
	 *
	 */
	private LocalDateTime startTime;
	/**
	 *
	 */
	private LocalDateTime completeTime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
