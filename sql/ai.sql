

-- AI调用配置表
CREATE TABLE `t_invoke_ai` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '调用名称',
  `description` varchar(500) DEFAULT '' COMMENT '功能描述',
  `script_engine` varchar(50) DEFAULT '' COMMENT '脚本引擎名称',
  `renderer_name` varchar(50) DEFAULT '' COMMENT '模板引擎名称',
  `input_validations` text COMMENT '入参校验规则（JSON字符串）',
  `input_conversion_script` text COMMENT '入参转换脚本',
  `system_prompt` text COMMENT 'AI的系统的提示词',
  `user_prompt` text COMMENT 'AI的用户的提示词',
  `tool_prompt` text COMMENT 'AI的工具的提示词',
  `handler_name` varchar(100) NOT NULL COMMENT 'AI处理器的名称',
  `method_name` varchar(100) NOT NULL COMMENT 'AI处理器方法名',
  `output_conversion_script` text COMMENT '出参转换脚本',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='AI调用配置表';




