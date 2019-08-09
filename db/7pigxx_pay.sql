USE pigxx_pay;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pay_channel
-- ----------------------------
DROP TABLE IF EXISTS `pay_channel`;
CREATE TABLE `pay_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '渠道主键ID',
  `mch_id` varchar(32) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '内部商户号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `channel_name` varchar(30) NOT NULL COMMENT '渠道名称,如:alipay,wechat',
  `channel_mch_id` varchar(32) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '渠道商户ID',
  `state` char(1) NOT NULL DEFAULT '0' COMMENT '渠道状态',
  `param` text NOT NULL COMMENT '配置参数,json字符串',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  `app_id` varchar(64) NOT NULL COMMENT 'appid',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_MchId_MchOrderNo` (`channel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4  COMMENT='支付渠道表';

-- ----------------------------
-- Records of pay_channel
-- ----------------------------
BEGIN;
INSERT INTO `pay_channel` VALUES (1, NULL, 'ALIPAY_WAP', '支付宝WAP支付', '2016102000727659', '0', '{\n    \"privateKey\":\"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCkejVpiLJlece9KLb8gG/+WODkpe5SRfr65n3aUAvRPoouDUp/URBAtt24mzHFdGkaU/Il7ZxG7KUjLaFNTfq8t/bHEF7HBt7MZidpEjVaDxeG/hkA+xc+X2ePAJXVhy7v1tH+XRrxuggTj+d4q7vVGD7r1mtgX4d5hK9TTKnyhO6IUcthPOUSn07wx+5wlbv4/51ggj4Ku4AWCGaCWiebDFy4VZ/FmOMP5u3O7TocCGSy5UtaFZaVMM1nH603DG6NJ8fG+zKum9IvrdQMMmBWPT8rpy0U9ztchcd+0gTNrTfE/NOCgl/RF6z7Qic7NiTKjLxkGRq73XPzfJx0hyfpAgMBAAECggEAa2/sndAN/90JjNUgmlVnUnRKCvEceJ9/rw6KXOV2oqrAZg6GgB26iRsqP6EYZMuCsBDvlrjcITQJNq5is/Vg+I8OYr+duVISjN+ZlLexI+/BxYsLWCmr6DE3myCdvwn7rezb5NR6ejWzetvALoG3Qx4AU9sO7rfX7ZevUrE8Pc5pa/rTCHOIec9P7Hfm4xrXd0DPmbAwFvUQ4spcC9xjcBhLJ3tfmkoEU11jC6Y/J7N0/TZZxqNC+HcYftSNEmbO3vxYfz/9mbabx7Uzp8xs4qHPJACmaP5tcWOCf1qJVOwP53Cd3H9J9Be9cAvlxKfT48s1ghoxLn7QB27p0UwntQKBgQDzTpCCFDoUH328B3iGTkwZq24DOUh5ZwhMG6VIFPLHwJYK6HVM473SX0nJWkbiBYFG4WiQWUZnOhz/qjTH1vvE/nzJwQ79zZahren2b8XulXKM6S/mafiBAHKl/FArcr14iAWkh+scZKWFKrNf6Hp8rQ6zu0fWdwzUOlaQFaSnTwKBgQCtDtpIagRPyNj0J39uMRJqsaY0TdJ53TD4bflf5UiFhNFH2Dikgw/uX8Pom2qtIvf3xnO2bzmJW2+OSkF7U0fL+UXkAaBHW+7YVcs8pWARSd5LWBXGWysV2wUALwynZSdwtgpgrzMQnuzklQ3hNR9LBSEGN7yJwkU83ZRWPzbvRwKBgQDU3E8g/oExSbu+3Opc1fNOIeTFfUAitjlUHHulbG5aw+qA8I5vDm/rtOHg/tI0u4w2bs4EO5aUiQsFwesbSsJJvjt+ZyCue0blfDnMGE2aRbVKAlidxOhcNAAZp3ycBm4tHROStjbDSGpm7syvg7xlhyHtrFNVFiJrKf7BX64FkQKBgCA8lAzJMuRp1YAlm2c7XOLjFMLJfFuXCHg+hCWI4Gl+xD1N2b9LarxMuoGp8cUurmJJZWSmc2FS1wT6cBg4+zbTyGEgrGqehW9nC+TQKYUO7Ym7btL0SKJZmiTenszP2vjz8Bryh+CguiAaY+t/qcSfv/cYitZeiec8n1UxkVohAoGAa8YwnzkEGBgz0TpdrGFxVBCqMIriMH4PQs2tWZ1nEBk6A7GrODRcRL0FQnRpHyf3mY5N/iEiNQ5GhQ5p1lifTvolTkVkdHpTGFhVfnYegaEAOznigi7dWJPoYlloN36AROxirGJKRIm+Od2bsybM/dNPJfoTDBDPFWGBH/klEBs=\",\n    \"publicKey\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuPkP2VJMR6vWCX8RwSFqNIa3klCdvRFJbuS1PN1anzQeeL9eOwtU7kGdI85yxb0dcdPzOYlG+jf9go8W9hBlgjxSRoXxLx03Yfl7cLmzJO9l9vIM1+HmNF0Ctm+el4Yi9dGs/P6q7lcHPUqs/RXGfeLrg33GMVwJbLmRcDZYeIcqPAA1OVF/4SHYr+f+O7glDOd60z+veOOexyoHmvUzYWlEz5+R4kOCNM/Z0w7KGgEYvHbZopexuTuFgUWy/9tYlNrnX+cZUWXVTskLUgD1UGWM1dS5+qfriqY9MPEwJjetcPJkoCK7A4IReE4q1DffUY9KS50/1ML+7na3R/p/UQIDAQAB\",\n    \"serviceUrl\":\"https://openapi.alipaydev.com/gateway.do\"\n}', '沙箱密码111111/11111', '0', '2019-06-19 03:01:21', '2019-06-19 03:01:21', 1, '2016102000727659');
COMMIT;

-- ----------------------------
-- Table structure for pay_goods_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_goods_order`;
CREATE TABLE `pay_goods_order` (
  `goods_order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品订单ID',
  `goods_id` varchar(30) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
  `amount` varchar(20) NOT NULL COMMENT '金额,单位元',
  `user_id` varchar(30) DEFAULT NULL COMMENT '用户ID',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)',
  `pay_order_id` varchar(30) DEFAULT NULL COMMENT '支付订单号',
  `del_flag` char(1) DEFAULT '0' COMMENT '0-正常,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`goods_order_id`),
  UNIQUE KEY `IDX_PayOrderId` (`pay_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='商品订单表';

-- ----------------------------
-- Table structure for pay_notify_record
-- ----------------------------
DROP TABLE IF EXISTS `pay_notify_record`;
CREATE TABLE `pay_notify_record` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `notify_id` varchar(50) NOT NULL COMMENT '响应ID',
  `request` varchar(2000) NOT NULL COMMENT '请求报文',
  `response` varchar(2000) NOT NULL COMMENT '响应报文',
  `order_no` varchar(50) NOT NULL COMMENT '系统订单号',
  `http_status` varchar(50) DEFAULT NULL COMMENT 'http状态',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='通知记录日志表';

-- ----------------------------
-- Table structure for pay_refund_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_refund_order`;
CREATE TABLE `pay_refund_order` (
  `refund_order_id` varchar(30) NOT NULL COMMENT '退款订单号',
  `pay_order_id` varchar(30) NOT NULL COMMENT '支付订单号',
  `channel_pay_order_no` varchar(64) DEFAULT NULL COMMENT '渠道支付单号',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `mch_refund_no` varchar(30) NOT NULL COMMENT '商户退款单号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `pay_amount` varchar(20) NOT NULL COMMENT '支付金额,单位元',
  `refund_amount` bigint(20) NOT NULL COMMENT '退款金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成',
  `result` tinyint(6) NOT NULL DEFAULT '0' COMMENT '退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `client_ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `channel_user` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
  `username` varchar(24) DEFAULT NULL COMMENT '用户姓名',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
  `channel_err_code` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
  `channel_err_msg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `notifyUrl` varchar(128) NOT NULL COMMENT '通知地址',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `expire_time` datetime DEFAULT NULL COMMENT '订单失效时间',
  `refund_succ_time` datetime DEFAULT NULL COMMENT '订单退款成功时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`refund_order_id`),
  UNIQUE KEY `IDX_MchId_MchOrderNo` (`mch_id`,`mch_refund_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款订单表';

-- ----------------------------
-- Table structure for pay_sequence
-- ----------------------------
DROP TABLE IF EXISTS `pay_sequence`;
CREATE TABLE `pay_sequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发号器表';

-- ----------------------------
-- Table structure for pay_trade_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_trade_order`;
CREATE TABLE `pay_trade_order` (
  `order_id` varchar(30) NOT NULL COMMENT '支付订单号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `amount` varchar(20) NOT NULL COMMENT '支付金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成',
  `client_ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `subject` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `body` varchar(256) NOT NULL COMMENT '商品描述信息',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(64) DEFAULT NULL COMMENT '渠道订单号',
  `err_code` varchar(64) DEFAULT NULL COMMENT '渠道支付错误码',
  `err_msg` varchar(128) DEFAULT NULL COMMENT '渠道支付错误描述',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `notify_url` varchar(128) DEFAULT NULL COMMENT '通知地址',
  `notify_count` tinyint(6) DEFAULT '0' COMMENT '通知次数',
  `last_notify_time` bigint(20) DEFAULT NULL COMMENT '最后一次通知时间',
  `expire_time` bigint(20) DEFAULT NULL COMMENT '订单失效时间',
  `pay_succ_time` bigint(20) DEFAULT NULL COMMENT '订单支付成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4  DEFAULT '0' COMMENT '删除标记',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='支付订单表';
SET FOREIGN_KEY_CHECKS = 1;