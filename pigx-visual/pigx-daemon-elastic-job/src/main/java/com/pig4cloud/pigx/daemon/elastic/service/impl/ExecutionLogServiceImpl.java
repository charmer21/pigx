package com.pig4cloud.pigx.daemon.elastic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.daemon.elastic.entity.ExecutionLog;
import com.pig4cloud.pigx.daemon.elastic.mapper.ExecutionLogMapper;
import com.pig4cloud.pigx.daemon.elastic.service.ExecutionLogService;
import org.springframework.stereotype.Service;

/**
 * 任务日志处理
 *
 * @author lishangbu
 * @date 2018/11/22
 */
@Service("executionLogService")
public class ExecutionLogServiceImpl extends ServiceImpl<ExecutionLogMapper, ExecutionLog> implements ExecutionLogService {

}
