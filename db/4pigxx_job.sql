/*
 my.cnf 设置数据库表名小写
 --lower_case_table_names=1
*/
USE pigxx_job;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `job_execution_log`
-- ----------------------------
DROP TABLE IF EXISTS `job_execution_log`;
CREATE TABLE `job_execution_log` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `hostname` varchar(255) NOT NULL,
  `ip` varchar(50) NOT NULL,
  `sharding_item` int(11) NOT NULL,
  `execution_source` varchar(20) NOT NULL,
  `failure_cause` varchar(4000) DEFAULT NULL,
  `is_success` int(11) NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `complete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='任务日志表';

-- ----------------------------
--  Table structure for `job_status_trace_log`
-- ----------------------------
DROP TABLE IF EXISTS `job_status_trace_log`;
CREATE TABLE `job_status_trace_log` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `original_task_id` varchar(255) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `slave_id` varchar(50) NOT NULL,
  `source` varchar(50) NOT NULL,
  `execution_type` varchar(20) NOT NULL,
  `sharding_item` varchar(100) NOT NULL,
  `state` varchar(20) NOT NULL,
  `message` varchar(4000) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `TASK_ID_STATE_INDEX` (`task_id`,`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='任务轨迹表';

-- ----------------------------
--  Table structure for `qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `qrtz_cron_triggers`
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', '测试任务', 'demo', '0 * * * * ? *', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
--  Table structure for `qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `qrtz_job_details`
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', '测试任务', 'demo', null, 'com.pig4cloud.pigx.daemon.quartz.config.PigxQuartzFactory', '0', '1', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000b7363686564756c654a6f627372002e636f6d2e70696734636c6f75642e706967782e6461656d6f6e2e71756172747a2e656e746974792e5379734a6f6200000000000000010200174c0009636c6173734e616d657400124c6a6176612f6c616e672f537472696e673b4c0008637265617465427971007e00094c000a63726561746554696d657400194c6a6176612f74696d652f4c6f63616c4461746554696d653b4c000e63726f6e45787072657373696f6e71007e00094c000b657865637574655061746871007e00094c00106a6f624578656375746553746174757371007e00094c00086a6f6247726f757071007e00094c00056a6f6249647400134c6a6176612f6c616e672f496e74656765723b4c00076a6f624e616d6571007e00094c00086a6f624f7264657271007e00094c00096a6f6253746174757371007e00094c000d6a6f6254656e616e745479706571007e00094c00076a6f625479706571007e00094c000a6d6574686f644e616d6571007e00094c00116d6574686f64506172616d7356616c756571007e00094c000d6d697366697265506f6c69637971007e00094c00086e65787454696d6571007e000a4c000c70726576696f757354696d6571007e000a4c000672656d61726b71007e00094c0009737461727454696d6571007e000a4c000874656e616e74496471007e000b4c0008757064617465427971007e00094c000a75706461746554696d6571007e000a78720035636f6d2e62616f6d69646f752e6d796261746973706c75732e657874656e73696f6e2e6163746976657265636f72642e4d6f64656c0000000000000001020000787074000464656d6f74000561646d696e7372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770a05000007e303190e00f17874000d30202a202a202a202a203f202a7400007400013074000464656d6f737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000174000ce6b58be8af95e4bbbbe58aa17400013574000133740001317400013274000a64656d6f4d6574686f6474000970696734636c6f7564740001337371007e0010770905000007e3031916e2787371007e0010770a05000007e30319161ccd78740020e6bc94e7a4ba537072696e67204265616ee79a84e5ae9ae697b6e4bbbbe58aa17371007e0010770a05000007e303191600de7871007e001874000561646d696e7371007e0010770a05000007e303190e00f1787800);
COMMIT;

-- ----------------------------
--  Table structure for `qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `qrtz_locks`
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'STATE_ACCESS'), ('clusteredScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
--  Table structure for `qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `qrtz_scheduler_state`
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_scheduler_state` VALUES ('clusteredScheduler', 'lengleng1553564320591', '1553568907589', '10000');
COMMIT;

-- ----------------------------
--  Table structure for `qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_IBFK_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_IBFK_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `qrtz_triggers`
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', '测试任务', 'demo', '测试任务', 'demo', null, '1553568960000', '1553568900000', '5', 'WAITING', 'CRON', '1553564598000', '0', null, '2', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `job_order` char(1) DEFAULT NULL COMMENT '组内执行顺利，值越大执行优先级越高，最大值9，最小值1',
  `job_type` char(1) NOT NULL DEFAULT '1' COMMENT '1、java类;2、spring bean名称;3、rest调用;4、jar调用;9其他',
  `execute_path` varchar(500) DEFAULT NULL COMMENT 'job_type=3时，rest调用地址，仅支持rest get协议,需要增加String返回值，0成功，1失败;job_type=4时，jar路径;其它值为空',
  `class_name` varchar(500) DEFAULT NULL COMMENT 'job_type=1时，类完整路径;job_type=2时，spring bean名称;其它值为空',
  `method_name` varchar(500) DEFAULT NULL COMMENT '任务方法',
  `method_params_value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '错失执行策略（1错失周期立即执行 2错失周期执行一次 3下周期执行）',
  `job_tenant_type` char(1) DEFAULT '1' COMMENT '1、多租户任务;2、非多租户任务',
  `job_status` char(1) DEFAULT '0' COMMENT '状态（1、未发布;2、运行中;3、暂停;4、删除;）',
  `job_execute_status` char(1) DEFAULT '0' COMMENT '状态（0正常 1异常）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '初次执行时间',
  `previous_time` timestamp NULL DEFAULT NULL COMMENT '上次执行时间',
  `next_time` timestamp NULL DEFAULT NULL COMMENT '下次执行时间',
  `tenant_id` int(11) DEFAULT '1' COMMENT '租户',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度表';

-- ----------------------------
--  Records of `sys_job`
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` VALUES ('1', '测试任务', 'demo', '5', '2', '', 'demo', 'demoMethod', 'pig4cloud', '0 * * * * ? *', '3', '1', '2', '0', 'admin', '2019-03-25 14:00:14', 'admin', '2019-03-25 14:00:14', '2019-03-26 09:43:18', '2019-03-26 10:55:00', '2019-03-26 10:56:00', '1', '演示Spring Bean的定时任务');
COMMIT;

-- ----------------------------
--  Table structure for `sys_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_id` int(11) NOT NULL COMMENT '任务id',
  `job_name` varchar(64) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(64) DEFAULT NULL COMMENT '任务组名',
  `job_order` char(1) DEFAULT NULL COMMENT '组内执行顺利，值越大执行优先级越高，最大值9，最小值1',
  `job_type` char(1) NOT NULL DEFAULT '1' COMMENT '1、java类;2、spring bean名称;3、rest调用;4、jar调用;9其他',
  `execute_path` varchar(500) DEFAULT NULL COMMENT 'job_type=3时，rest调用地址，仅支持post协议;job_type=4时，jar路径;其它值为空',
  `class_name` varchar(500) DEFAULT NULL COMMENT 'job_type=1时，类完整路径;job_type=2时，spring bean名称;其它值为空',
  `method_name` varchar(500) DEFAULT NULL COMMENT '任务方法',
  `method_params_value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron执行表达式',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `job_log_status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `execute_time` varchar(30) DEFAULT NULL COMMENT '执行时间',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tenant_id` int(11) NOT NULL DEFAULT '1' COMMENT '租户id',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务执行日志表';

SET FOREIGN_KEY_CHECKS = 1;
