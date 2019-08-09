/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost
 Source Database       : pigxx_mp

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 04/15/2019 22:16:01 PM
*/
USE pigxx_mp;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `rp_notify_record`
-- ----------------------------
DROP TABLE IF EXISTS `rp_notify_record`;
CREATE TABLE `rp_notify_record` (
  `id` varchar(50) NOT NULL,
  `version` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '修改者',
  `creater` varchar(100) DEFAULT NULL COMMENT '创建者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `notify_times` int(11) NOT NULL,
  `limit_notify_times` int(11) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `merchant_order_no` varchar(50) NOT NULL,
  `merchant_no` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL COMMENT '100:成功 101:失败',
  `notify_type` varchar(30) DEFAULT NULL COMMENT '通知类型',
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`merchant_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='通知记录表 rp_notify_record';

-- ----------------------------
--  Table structure for `wx_account`
-- ----------------------------
DROP TABLE IF EXISTS `wx_account`;
CREATE TABLE `wx_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '公众号名称',
  `account` varchar(100) DEFAULT NULL COMMENT '公众号账户',
  `appid` varchar(100) DEFAULT NULL COMMENT '公众号appid',
  `appsecret` varchar(100) DEFAULT NULL COMMENT '公众号密钥',
  `url` varchar(100) DEFAULT NULL COMMENT '公众号url',
  `token` varchar(100) DEFAULT NULL COMMENT '公众号token',
  `aeskey` varchar(300) DEFAULT NULL COMMENT '加密密钥',
  `qr_url` varchar(200) DEFAULT NULL COMMENT '二维码图片URL',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='公众号账户表';

-- ----------------------------
--  Records of `wx_account`
-- ----------------------------
BEGIN;
INSERT INTO `wx_account` VALUES ('1', 'pigx测试', 'gh_a3e7ca4a4e25', 'wxc7cf3d360cf5785e', '3d7a89ccf680c649453ec4806ca4bb58', 'http://pigx.huaxiadaowei.com', 'pigx', 'pigx', 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQF88TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyWm1EOXNPWEdlS0MxMDAwMGcwM2MAAgQEzslcAwQAAAAA', '2019-05-02 22:14:35', '2019-05-02 22:14:35', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `wx_account_fans`
-- ----------------------------
DROP TABLE IF EXISTS `wx_account_fans`;
CREATE TABLE `wx_account_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户标识',
  `subscribe_status` char(1) CHARACTER SET utf8 DEFAULT NULL COMMENT '订阅状态，0未关注，1已关注',
  `subscribe_time` datetime DEFAULT NULL COMMENT '订阅时间',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别，1男，2女，0未知',
  `language` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '语言',
  `country` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '国家',
  `province` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份',
  `city` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '城市',
  `headimg_url` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像地址',
  `remark` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `wx_account_id` int(11) DEFAULT NULL COMMENT '微信公众号ID',
  `wx_account_name` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `wx_account_appid` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信公众号appid',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '删除标记',
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_1` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='微信公众号粉丝表';

-- ----------------------------
--  Records of `wx_account_fans`
-- ----------------------------
BEGIN;
INSERT INTO `wx_account_fans` VALUES ('1', 'o-2qu5vMc-a9Z9XiAUU2Xgc19aE4', '1', '2019-05-02 15:30:21', '冷冷', '1', 'zh_CN', '中国', '山东', '潍坊', 'http://thirdwx.qlogo.cn/mmopen/xWVKBUsSrXVFu38B20sms4MQBG55sN8dslTqIJMUpialGLK5Lo9hxkCV04eydI31TvEPr9BvsqD8TnqgKYCoCHyhTl23ica7CT/132', '', '1', 'pigx测试', 'wxc7cf3d360cf5785e', '2019-05-02 22:15:27', '2019-05-02 22:15:27', '0', '1'), ('2', 'o-2qu5iLqF73HgDGJBNVBWuOKzW4', '1', '2019-05-02 00:55:04', 'pig4cloud', '1', 'zh_CN', '中国', '北京', '朝阳', 'http://thirdwx.qlogo.cn/mmopen/wOOYCtiaKvvH1LgndyO7OcGbziaeeSGSanrDouIxtBhdqibnngGJYeDnjAooCNJduA5OnNUDZgjCSXiaa77zuvuOwDXrVQqyD2On/132', '', '1', 'pigx测试', 'wxc7cf3d360cf5785e', '2019-05-02 22:15:27', '2019-05-02 22:15:27', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `wx_fans_msg`
-- ----------------------------
DROP TABLE IF EXISTS `wx_fans_msg`;
CREATE TABLE `wx_fans_msg` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(100) DEFAULT NULL COMMENT '用户标识',
  `nickname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `headimg_url` varchar(500) DEFAULT NULL COMMENT '头像地址',
  `wx_account_appid` varchar(255) DEFAULT NULL,
  `wx_account_name` varchar(255) DEFAULT NULL,
  `wx_account_id` int(11) DEFAULT NULL COMMENT '微信账号ID',
  `msg_id` bigint(20) DEFAULT NULL COMMENT '消息ID',
  `msg_type` varchar(32) DEFAULT NULL COMMENT '消息类型',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `res_content` varchar(3000) DEFAULT NULL COMMENT '最近一条回复内容',
  `is_res` char(1) DEFAULT '0' COMMENT '是否已回复',
  `media_id` varchar(100) DEFAULT NULL COMMENT '微信素材ID',
  `pic_url` varchar(500) DEFAULT NULL COMMENT '微信图片URL',
  `pic_path` varchar(500) DEFAULT NULL COMMENT '本地图片路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='粉丝消息表 ';

-- ----------------------------
--  Records of `wx_fans_msg`
-- ----------------------------
BEGIN;
INSERT INTO `wx_fans_msg` VALUES ('1', 'o-2qu5iLqF73HgDGJBNVBWuOKzW4', 'pig4cloud', 'http://thirdwx.qlogo.cn/mmopen/wOOYCtiaKvvH1LgndyO7OcGbziaeeSGSanrDouIxtBhdqibnngGJYeDnjAooCNJduA5OnNUDZgjCSXiaa77zuvuOwDXrVQqyD2On/132', 'wxc7cf3d360cf5785e', 'pigx测试', '1', '22288113600209489', 'text', '1016', null, '1', null, null, null, '2019-05-02 22:16:17', '2019-05-02 22:16:17', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `wx_fans_msg_res`
-- ----------------------------
DROP TABLE IF EXISTS `wx_fans_msg_res`;
CREATE TABLE `wx_fans_msg_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fans_msg_id` int(11) DEFAULT NULL COMMENT '粉丝消息ID',
  `res_content` text COMMENT '回复内容',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标识',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='回复粉丝消息历史表 ';

-- ----------------------------
--  Records of `wx_fans_msg_res`
-- ----------------------------
BEGIN;
INSERT INTO `wx_fans_msg_res` VALUES ('1', '1', '你好', '1', 'admin', '2019-05-02 22:17:05', '2019-05-02 22:17:05', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `wx_mp_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_menu`;
CREATE TABLE `wx_mp_menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu` json DEFAULT NULL COMMENT '菜单',
  `wx_account_id` int(11) DEFAULT NULL,
  `wx_account_appid` varchar(100) DEFAULT NULL,
  `wx_account_name` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  `pub_flag` char(1) DEFAULT '0' COMMENT '发布标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='微信菜单表';

-- ----------------------------
--  Records of `wx_mp_menu`
-- ----------------------------
BEGIN;
INSERT INTO `wx_mp_menu` VALUES ('17', '{}', null, null, null, null, null, null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;