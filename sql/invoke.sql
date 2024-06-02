

-- 调用相关配置表
CREATE TABLE `t_invoke_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '配置名称',
  `description` varchar(500) DEFAULT '' COMMENT '配置描述',
  `content` text COMMENT '配置内容（一般情况下都是JSON字符串）',
  `platform` varchar(50) NOT NULL DEFAULT '0' COMMENT '平台信息：0 未知',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID（0表示无租户）',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_user` bigint(20) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='调用相关配置表';


-- Http 调用配置表
CREATE TABLE `t_invoke_http` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '调用名称',
  `description` varchar(500) DEFAULT '' COMMENT '功能描述',
  `script_engine` varchar(50) DEFAULT '' COMMENT '脚本引擎名称',
  `charset` varchar(20) DEFAULT '' COMMENT '字符集',
  `method` tinyint(4) NOT NULL COMMENT '方法：1 get，2 post，3 put，4 delete',
  `url` varchar(500) NOT NULL COMMENT 'URL地址',
  `headers` text COMMENT '请求头（JSON字符串）',
  `input_type` tinyint(4) DEFAULT '1' COMMENT '入参类型：1 no content，2 form-www，3 form-data，4 json',
  `parameters` text COMMENT '表单类参数（JSON字符串）',
  `body` text COMMENT '文本类入参转换脚本',
  `input_validations` text COMMENT '入参校验规则（JSON字符串）',
  `output_type` tinyint(4) DEFAULT '2' COMMENT '出参类型：1 no content，2 raw data，3 json',
  `output` text COMMENT '出参转换脚本',
  `output_validations` text COMMENT '出参校验规则（JSON字符串）',
  `cache_config` text COMMENT '缓存配置（JSON字符串，Java对象为 Map）',
  `config_id` bigint(20) DEFAULT NULL COMMENT '配置ID（otherConfigs中填充的内容）',
  `platform` varchar(50) NOT NULL DEFAULT '0' COMMENT '平台信息：0 未知',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID（0表示无租户）',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_user` bigint(20) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_method` (`method`) USING BTREE,
  KEY `idx_url` (`url`) USING BTREE,
  KEY `idx_config_id` (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='Http调用配置表';


-- Jdbc 调用配置表
CREATE TABLE `t_invoke_jdbc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '调用名称',
  `description` varchar(500) DEFAULT '' COMMENT '功能描述',
  `script_engine` varchar(50) DEFAULT '' COMMENT '脚本引擎名称',
  `renderer_name` varchar(50) DEFAULT '' COMMENT '模板引擎名称',
  `input_validations` text COMMENT '入参校验规则（JSON字符串）',
  `input` text COMMENT '入参转换脚本',
  `execute_type` tinyint(4) NOT NULL COMMENT '执行类型：2 更新，3 批量更新，4 单值查询，5 单对象查询，6 对象数组查询，7 对象数组分页查询，8 对象数组分页滚动查询',
  `sql` text COMMENT '要执行的SQL',
  `output` text COMMENT '出参转换脚本',
  `output_validations` text COMMENT '出参校验规则（JSON字符串）',
  `cache_config` text COMMENT '缓存配置（JSON字符串，Java对象为 Map）',
  `config_id` bigint(20) DEFAULT NULL COMMENT '配置ID（otherConfigs中填充的内容）',
  `platform` varchar(50) NOT NULL DEFAULT '0' COMMENT '平台信息：0 未知',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID（0表示无租户）',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_user` bigint(20) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_config_id` (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='Jdbc调用配置表';


-- 调用日志相关表
CREATE TABLE `t_invoke_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` tinyint(4) NOT NULL COMMENT '类型：1 Http，2 SQL，3 脚本',
  `invoke_name` varchar(40) NOT NULL COMMENT '调用名称',
  `time` datetime NOT NULL COMMENT '发生时间',
  `expected_class` varchar(200) NOT NULL COMMENT '期望的类型',
  `raw_input` text COMMENT '原始入参',
  `config` text COMMENT '对应的配置信息',
  `converted_input` text COMMENT '转换后的入参',
  `raw_output` longtext COMMENT '原始出参',
  `converted_output` longtext COMMENT '转换后的出参',
  `error` text COMMENT '发生的错误信息',
  `platform` varchar(50) NOT NULL DEFAULT '0' COMMENT '平台信息：0 未知',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID（0表示无租户）',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_user` bigint(20) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_invoke_name` (`invoke_name`) USING BTREE,
  KEY `idx_time` (`time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='调用日志相关表';


-- 脚本调用配置表
CREATE TABLE `t_invoke_script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '调用名称',
  `script_name` varchar(300) NOT NULL COMMENT '脚本名称',
  `script_description` varchar(600) DEFAULT '' COMMENT '脚本描述',
  `script_engine` varchar(50) DEFAULT '' COMMENT '脚本引擎名称',
  `script_content` text COMMENT '脚本内容',
  `input_validations` text COMMENT '入参校验规则（JSON字符串）',
  `output_validations` text COMMENT '出参校验规则（JSON字符串）',
  `cache_config` text COMMENT '缓存配置（JSON字符串，Java对象为 Map）',
  `config_id` bigint(20) DEFAULT NULL COMMENT '配置ID（otherConfigs中填充的内容）',
  `platform` varchar(50) NOT NULL DEFAULT '0' COMMENT '平台信息：0 未知',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID（0表示无租户）',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_user` bigint(20) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_script_name` (`script_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='脚本调用配置表';




-- 调用相关配置表期初数据
INSERT INTO `t_invoke_config`(`id`, `name`, `description`, `content`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (1, '天眼查配置', '', '{\r\n\"authorization\":\"ffffffff-0000-0000-0000-ffffffffffff\"\r\n}', '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);
INSERT INTO `t_invoke_config`(`id`, `name`, `description`, `content`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (2, '快递100配置', '', '{\r\n\"key\":\"DDDWWWFFFAAA\",\r\n\"customer\":\"00000000000000000000000000000000\"\r\n}', '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);


-- Http 调用配置表期初数据
INSERT INTO `t_invoke_http`(`id`, `name`, `description`, `script_engine`, `charset`, `method`, `url`, `headers`, `input_type`, `parameters`, `body`, `input_validations`, `output_type`, `output`, `output_validations`, `cache_config`, `config_id`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (1, 'test:baidu-web', '三方：测试 - 百度首页网页', 'nashorn', 'utf-8', 1, '\"https://www.baidu.com\"', NULL, 1, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);
INSERT INTO `t_invoke_http`(`id`, `name`, `description`, `script_engine`, `charset`, `method`, `url`, `headers`, `input_type`, `parameters`, `body`, `input_validations`, `output_type`, `output`, `output_validations`, `cache_config`, `config_id`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (2, 'tx:wx-work-robot-send', '三方：企微消息发送', 'nashorn', 'utf-8', 2, '\"https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=ffffffff-0000-0000-0000-bbbbbbbbbbbb\"', 'java.util.Arrays.asList([\r\n{\"key\":\"Content-Type\", \"value\":\"application/json; charset=\"+config.charset||\"utf-8\"}\r\n])', 4, NULL, 'var result = {\r\n    \"msgtype\":\"text\",\r\n    \"text\": {\r\n        \"content\": rawInput.message\r\n    }\r\n};\r\nresult;', '[\r\n{\"expression\":\"rawInput.message\", \"validator\":\"not_blank\", \"message\":\"待发送的消息不能为空！\"}\r\n]', 3, 'rawOutput.rawObject;', '[\r\n{\"expression\":\"rawOutput.statusCode==200\", \"validator\":\"is_true\", \"message\":\"接口调用失败！\"}\r\n]', '', NULL, '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);
INSERT INTO `t_invoke_http`(`id`, `name`, `description`, `script_engine`, `charset`, `method`, `url`, `headers`, `input_type`, `parameters`, `body`, `input_validations`, `output_type`, `output`, `output_validations`, `cache_config`, `config_id`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (3, 'tyc:company-base-info-v3-2', '三方：天眼查 - 企业基本信息（含主要人员）（接口文档：https://open.tianyancha.com/open/819）', 'nashorn', 'utf-8', 1, '\"http://open.api.tianyancha.com/services/open/ic/baseinfoV3/2.0?keyword=\"+encodeURI(rawInput.keyword)', 'java.util.Arrays.asList([\r\n{\"key\":\"Content-Type\", \"value\":\"application/json; charset=\"+config.charset||\"utf-8\"},\r\n{\"key\":\"Authorization\", \"value\":config.otherConfigs.authorization}\r\n])', 1, NULL, NULL, '[\r\n{\"expression\":\"rawInput.keyword\", \"validator\":\"not_blank\", \"message\":\"搜索关键字不能为空！\"}\r\n]', 3, 'rawOutput.rawObject.result', '[\r\n{\"expression\":\"rawOutput.rawObject.error_code==0||rawOutput.rawObject.error_code==300000\", \"validator\":\"is_true\", \"message\":\"接口调用失败！\"}\r\n]', '{\r\n\"cacheName\":\"jdbc-60d\",\r\n\"cacheKey\":\"invokeName + \\\"-\\\" + rawInput.keyword\"\r\n}', 1, '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);
INSERT INTO `t_invoke_http`(`id`, `name`, `description`, `script_engine`, `charset`, `method`, `url`, `headers`, `input_type`, `parameters`, `body`, `input_validations`, `output_type`, `output`, `output_validations`, `cache_config`, `config_id`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (4, 'tyc:company-holder-2-0', '三方：天眼查 - 企业股东（接口文档：https://open.tianyancha.com/open/821）', 'nashorn', 'utf-8', 1, '\"http://open.api.tianyancha.com/services/open/ic/holder/2.0?keyword=\"+encodeURI(rawInput.keyword) + \"&pageNum=\" + rawInput.pageNum + \"&pageSize=\" + rawInput.pageSize', 'java.util.Arrays.asList([\r\n{\"key\":\"Content-Type\", \"value\":\"application/json; charset=\"+config.charset||\"utf-8\"},\r\n{\"key\":\"Authorization\", \"value\":config.otherConfigs.authorization}\r\n])', 1, NULL, NULL, '[\r\n{\"expression\":\"rawInput.keyword\", \"validator\":\"not_blank\", \"message\":\"搜索关键字不能为空！\"},\r\n{\"expression\":\"rawInput.pageNum\", \"validator\":\"not_null\", \"message\":\"页码不能为空！\"},\r\n{\"expression\":\"rawInput.pageSize\", \"validator\":\"not_null\", \"message\":\"每页大小不能为空！\"}\r\n]', 3, 'rawOutput.rawObject.result', '[\r\n{\"expression\":\"rawOutput.rawObject.error_code==0||rawOutput.rawObject.error_code==300000\", \"validator\":\"is_true\", \"message\":\"接口调用失败！\"}\r\n]', '{\r\n\"cacheName\":\"jdbc-60d\",\r\n\"cacheKey\":\"invokeName + \\\"-\\\" + rawInput.keyword + \\\"-\\\" + rawInput.pageNum + \\\"-\\\" + rawInput.pageSize\"\r\n}', 1, '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);
INSERT INTO `t_invoke_http`(`id`, `name`, `description`, `script_engine`, `charset`, `method`, `url`, `headers`, `input_type`, `parameters`, `body`, `input_validations`, `output_type`, `output`, `output_validations`, `cache_config`, `config_id`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (5, 'kd100:delivery-sn-recognize', '三方：快递100 - 单号识别', 'nashorn', 'utf-8', 1, '\"http://www.kuaidi100.com/autonumber/auto?num=\"+rawInput.deliverySn+\"&key=\"+config.otherConfigs.key', 'java.util.Arrays.asList([\r\n{\"key\":\"Content-Type\", \"value\":\"application/json; charset=\"+config.charset||\"utf-8\"}\r\n])', 1, NULL, NULL, '[\r\n{\"expression\":\"rawInput.deliverySn\", \"validator\":\"not_blank\", \"message\":\"物流单号不能为空！\"}\r\n]', 3, 'var result;\r\nif (rawOutput.rawObject != null && rawOutput.rawObject.length != 0) {\r\n    result = rawOutput.rawObject[0];\r\n}\r\nresult;', '[\r\n{\"expression\":\"rawOutput.statusCode==200\", \"validator\":\"is_true\", \"message\":\"接口调用失败！\"}\r\n]', '{\r\n\"cacheName\":\"jdbc-60d\",\r\n\"cacheKey\":\"invokeName + \\\"-\\\" + rawInput.deliverySn\"\r\n}', 2, '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);
INSERT INTO `t_invoke_http`(`id`, `name`, `description`, `script_engine`, `charset`, `method`, `url`, `headers`, `input_type`, `parameters`, `body`, `input_validations`, `output_type`, `output`, `output_validations`, `cache_config`, `config_id`, `platform`, `tenant_id`, `create_user`, `create_time`, `modify_user`, `modify_time`, `delete_status`) VALUES (6, 'kd100:logistics-info-query', '三方：快递100 - 物流信息查询', 'nashorn', 'utf-8', 2, '\"https://poll.kuaidi100.com/poll/query.do\"', 'java.util.Arrays.asList([\r\n{\"key\":\"Content-Type\", \"value\":\"application/x-www-form-urlencoded; charset=\"+config.charset||\"utf-8\"}\r\n])', 2, 'var DigestUtil = Java.type(\"cn.hutool.crypto.digest.DigestUtil\");\r\nvar data = {\r\n    \"com\":rawInput.companyCode,\r\n    \"num\":rawInput.deliverySn,\r\n    \"resultv2\":4,\r\n    \"order\":\"desc\"\r\n};\r\nvar param = JSON.stringify(data);\r\nvar sign = DigestUtil.md5Hex(param + config.otherConfigs.key + config.otherConfigs.customer, \"utf-8\").toUpperCase();\r\nvar result = [{}, {}, {}];\r\nresult[0] = {\"key\":\"param\", \"value\":param};\r\nresult[1] = {\"key\":\"sign\", \"value\":sign};\r\nresult[2] = {\"key\":\"customer\", \"value\":config.otherConfigs.customer};\r\njava.util.Arrays.asList(result);', NULL, '[\r\n{\"expression\":\"rawInput.companyCode\", \"validator\":\"not_blank\", \"message\":\"物流公司编码不能为空！\"},\r\n{\"expression\":\"rawInput.deliverySn\", \"validator\":\"not_blank\", \"message\":\"物流单号不能为空！\"}\r\n]', 3, '', '[\r\n{\"expression\":\"rawOutput.statusCode == 200\", \"validator\":\"is_true\", \"message\":\"接口调用失败！\"},\r\n{\"expression\":\"rawOutput.rawObject.status == 200\", \"validator\":\"is_true\", \"message\":\"接口调用失败！\"}\r\n]', NULL, 2, '0', 0, 1, '2024-06-02 12:00:00', 1, '2024-06-02 12:00:00', 0);












