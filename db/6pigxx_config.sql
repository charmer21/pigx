USE pigxx_config;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info` VALUES (1, 'application-dev.yml', 'DEFAULT_GROUP', 'jasypt:\n  encryptor:\n    password: pigx\n\nspring:\n  redis:\n    host: pigx-redis\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n      \nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\nfeign:\n  hystrix:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n# hystrix If you need to use ThreadLocal bound variables in your RequestInterceptor`s\n# you will need to either set the thread isolation strategy for Hystrix to `SEMAPHORE or disable Hystrix in Feign.\nhystrix:\n  command:\n    default:\n      execution:\n        isolation:\n          strategy: SEMAPHORE\n          thread:\n            timeoutInMilliseconds: 60000\n  shareSecurityContext: true\n\n\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n\n\nmybatis-plus:\n  tenant-enable: ture\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  global-config:\n    banner: false\n    db-config:\n      id-type: auto\n      field-strategy: NOT_EMPTY\n\nswagger:\n  title: PigX Swagger API\n  license: Powered By PigX\n  licenseUrl: https://pig4cloud.com/\n  terms-of-service-url: https://pig4cloud.com/\n  contact:\n    email: wangiegie@gmail.com\n    url: https://pig4cloud.com/about.html\n  authorization:\n    name: pigX OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${GATEWAY-HOST:pigx-gateway}:${GATEWAY-PORT:9999}/auth/oauth/token\n\nsecurity:\n  oauth2:\n    client:\n      ignore-urls:\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://${AUTH-HOST:pigx-auth}/oauth/check_token\n\n#dsdsa来电二中文呀', 'ca13daa103a02604d5cca96b63a404f1', '2019-04-18 02:10:20', '2019-07-15 17:07:46', NULL, '127.0.0.1', '', '', '通用配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (2, 'pigx-activiti-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(gPFcUOmJm8WqM3k3eSqS0Q==)\n      client-secret: ENC(gPFcUOmJm8WqM3k3eSqS0Q==)\n      scope: server\n      ignore-urls:\n        - \'/actuator/**\'\n        - \'/v2/api-docs\'\n        - \'/editor-app/**\'\n        - \'/modeler.html\'\n        - \'/ws/**\'\nspring:\n  autoconfigure:\n    exclude: org.activiti.spring.boot.SecurityAutoConfiguration\n  activiti:\n    check-process-definitions: false\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx_ac}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n\n# 租户表维护\npigx:\n  tenant:\n    column: tenant_id\n    tables:\n      - oa_leave_bill', 'f9ae6f353c354f3a398373a6fe93e543', '2019-04-18 02:10:56', '2019-05-14 15:14:56', NULL, '127.0.0.1', '', '', '工作流配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (3, 'pigx-auth-dev.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/', '7065ac5cb6d799bfd290ad3eaf19d838', '2019-04-18 02:11:32', '2019-05-28 18:14:28', NULL, '0:0:0:0:0:0:0:1', '', '', '认证中心配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (4, 'pigx-codegen-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(gPFcUOmJm8WqM3k3eSqS0Q==)\n      client-secret: ENC(gPFcUOmJm8WqM3k3eSqS0Q==)\n      scope: server\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n\npigx:\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_datasource_conf', '8ce46741c2060c7b2a9e7e968efc65fe', '2019-04-18 02:12:10', '2019-05-15 12:13:11', NULL, '127.0.0.1', '', '', '代码生成', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (5, 'pigx-daemon-elastic-job-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(tz2NM4GcmnE7sNJTYL8ZSg==)\n      client-secret: ENC(tz2NM4GcmnE7sNJTYL8ZSg==)\n      scope: server\n## 定时任务\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx_job}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  elasticjob:\n    # 分布式任务协调依赖zookeeper\n    zookeeper:\n      server-lists: ${ZOOKEEPER-HOST:pigx-zookeeper}:${ZOOKEEPER-PORT:2181}\n      namespace: pigx-daemon\n    # 普通任务\n    simples:\n      spring-simple-job:\n        job-class: com.pig4cloud.pigx.daemon.elastic.job.PigxSimpleJob\n        cron: 0 0 0/1 * * ?\n        sharding-total-count: 3\n        sharding-item-parameters: 0=service1,1=service2,2=service3\n        eventTraceRdbDataSource: \'dataSource\'\n        listener:\n          listener-class: com.pig4cloud.pigx.daemon.elastic.listener.PigxElasticJobListener\n      spring-simple-job2:\n        job-class: com.pig4cloud.pigx.daemon.elastic.job.PigxSimpleJob2\n        cron: 0 0 0/1 * * ?\n        sharding-total-count: 3\n        sharding-item-parameters: 0=service1,1=service2,2=service3\n        eventTraceRdbDataSource: \'dataSource\'\n        listener:\n          listener-class: com.pig4cloud.pigx.daemon.elastic.listener.PigxElasticJobListener\n    # 简单任务\n    dataflows:\n      spring-dataflow-job:\n        job-class: com.pig4cloud.pigx.daemon.elastic.job.PigxDataflowJob\n        cron: 0 0 0/1 * * ?\n        sharding-total-count: 3\n        sharding-item-parameters: 0=service1,1=service2,2=service3\n        streaming-process: true\n        eventTraceRdbDataSource: \'dataSource\'\n        listener:\n          distributed-listener-class: com.pig4cloud.pigx.daemon.elastic.listener.PigxDistributeElasticJobListener\n          started-timeout-milliseconds: 5000\n          completed-timeout-milliseconds: 10000', 'a66ba457dd1fc231ce95e9aaebe57dfc', '2019-04-18 02:12:57', '2019-05-14 15:16:32', NULL, '127.0.0.1', '', '', '定时任务-elastic-job配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (7, 'pigx-gateway-dev.yml', 'DEFAULT_GROUP', 'security:\n  encode:\n    key: \'pigxpigxpigxpigx\'\n\nignore:\n  clients:\n    - test\n  swagger-providers:\n    - ${AUTH-HOST:pigx-auth}\n    - ${TX-MGR-HOST:pigx-tx-manager}\n\nribbon:\n  rule:\n    gray-enabled: true', '37c3e9b507c963697af7f41a9b13add3', '2019-04-18 02:13:52', '2019-07-02 15:13:23', NULL, '172.16.1.3', '', '', '网关配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (8, 'pigx-monitor-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # 安全配置\n  security:\n    user:\n      name: ENC(rZHA4LW5hHmhLAAzJoFNag==)     # pigx\n      password: ENC(bjeyh+Aeii3kHXkoo00ZUw==) # pigx\n  boot:\n    admin:\n      ui:\n        resource-locations: \'classpath:/ui/,classpath:/static/\'\n        template-location: \'classpath:/ui/\'\n        title: \'pigx 服务状态监控\'\n        brand: \'pigx 服务状态监控\'', 'd4caef8a7cad3d357ad2fcb9f57e9982', '2019-04-18 02:14:17', '2019-04-18 02:14:17', NULL, '127.0.0.1', '', '', '监控配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (14, 'pigx-upms-biz-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(ltJPpR50wT0oIY9kfOe1Iw==)\n      client-secret: ENC(ltJPpR50wT0oIY9kfOe1Iw==)\n      scope: server\n      ignore-urls:\n        - /druid/**\n        - /actuator/**\n        - /v2/api-docs\n\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        #login-username: admin\n        #login-password: admin\n      filter:\n        stat:\n          enabled: true\n          log-slow-sql: true\n          slow-sql-millis: 10000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n\n# 文件系统 (提供测试环境，不要乱传)\nminio:\n  url: http://minio.pig4cloud.com\n  access-key: lengleng\n  secret-key: lengleng\n\n# Logger Config\nlogging:\n  level:\n    com.pig4cloud.pigx.admin.mapper: debug\n\n# 租户表维护\npigx:\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_user\n      - sys_role\n      - sys_dept\n      - sys_log\n      - sys_social_details\n      - sys_dict\n      - sys_dict_item\n      - sys_public_param\n      - sys_log\n      - sys_file', '096e7c44aa28a4721fc4275073aa939a', '2019-04-18 02:32:44', '2019-06-29 13:12:54', NULL, '10.1.1.240', '', '', 'admin 服务配置', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (15, 'pigx-tx-manager-dev.yml', 'DEFAULT_GROUP', '# 页面配置\nspring:\n  mvc:\n    static-path-pattern: /**\n  resources:\n    static-locations: classpath:/static/\n\n\n# LCN 配置\ntm:\n  transaction:\n    netty:\n      delaytime: 5   # 客户端链接最大通讯时间 （秒）\n      hearttime: 15  # 客户端心跳时间   （秒）\n  redis:\n    savemaxtime: 30  # redis 保存时间  （秒）\n  socket:\n    port: 9998       # 通讯端口\n    maxconnection: 1000  #最大链接数\n  compensate:\n    auto: false   #是否自动补偿\n    notifyUrl: http://ip:port/path #补偿结果通知（配消息总线里面）\n    tryTime: 30     # z再次重试时间间隔\n    maxWaitTime: 5000   # 请求超时的最大时间 (毫秒)', '885f5c125f2df7a08692929ccce154be', '2019-04-18 02:54:03', '2019-04-18 02:54:03', NULL, '127.0.0.1', '', '', '分布式事务协调模块', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (17, 'pigx-mp-manager-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(vW+Nup9LaTfIFwgufUBsYg==)\n      client-secret: ENC(vW+Nup9LaTfIFwgufUBsYg==)\n      scope: server\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx_mp}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n\n\n# 租户表维护\npigx:\n  tenant:\n    column: tenant_id\n    tables:\n      - wx_menu\n      - wx_account\n      - wx_account_fans\n      - wx_fans_msg_res', '1516b4bae5c4a38c39ba4a9d205929f3', '2019-04-18 03:00:10', '2019-05-14 15:17:45', NULL, '127.0.0.1', '', '', '公众号管理模块', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (18, 'pigx-daemon-quartz-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(tz2NM4GcmnE7sNJTYL8ZSg==)\n      client-secret: ENC(tz2NM4GcmnE7sNJTYL8ZSg==)\n      scope: server\n\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx_job}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n  quartz:\n    #相关属性配置\n    properties:\n      org:\n        quartz:\n          scheduler:\n            instanceName: clusteredScheduler\n            instanceId: AUTO\n          jobStore:\n            class: org.quartz.impl.jdbcjobstore.JobStoreTX\n            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate\n            tablePrefix: QRTZ_\n            isClustered: true\n            clusterCheckinInterval: 10000\n            useProperties: false\n          threadPool:\n            class: org.quartz.simpl.SimpleThreadPool\n            threadCount: 50\n            threadPriority: 5\n            threadsInheritContextClassLoaderOfInitializingThread: true\n    #数据库方式\n    job-store-type: jdbc\n    #初始化表结构\n    #jdbc:\n    #initialize-schema: never\n\n', 'aa30eacf6be9e3638d9f977c703089a2', '2019-04-18 03:08:34', '2019-05-14 15:18:28', NULL, '127.0.0.1', '', '', 'quartz 配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (19, 'pigx-pay-dev.yml', 'DEFAULT_GROUP', '## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(gPFcUOmJm8WqM3k3eSqS0Q==)\n      client-secret: ENC(gPFcUOmJm8WqM3k3eSqS0Q==)\n      scope: server\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:pigx-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:pigxx_pay}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n\n# 租户表维护\npigx:\n  pay:\n    test: lengleng\n    aliPayConfig:\n      expire-time: 30\n      return-url: http://pig4cloud.com\n      notify-url: http://pigx.huaxiadaowei.com/pay/notify/ali/callbak\n    wxPayConfig:\n      notify-url: http://pigx.huaxiadaowei.com/pay/notify/wx/callbak\n  xsequence:    #发号器相关配置\n    db:\n      retry-times: 3\n      table-name: pay_sequence\n  tenant:\n    column: tenant_id\n    tables:\n      - pay_channel\n      - pay_trade_order\n      - pay_goods_order\n      - pay_notify_record', '6dec22a38eee06ae08bb73b84f07ef31', '2019-05-28 11:46:27', '2019-07-02 09:55:07', NULL, '172.16.1.3', '', '', '支付模块配置', NULL, NULL, 'yaml', NULL);
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='增加租户字段';

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_beta';

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_tag';

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_tag_relation';

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='集群、各Group容量信息表';

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text COLLATE utf8_bin,
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='多租户改造';

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='租户容量信息表';

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='tenant_info';

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;