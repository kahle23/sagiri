

CREATE TABLE `base_op_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '事件名称',
  `time` datetime NOT NULL COMMENT '时间',
  `message` text COMMENT '消息',
  `user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_type` varchar(50) NOT NULL DEFAULT '' COMMENT '用户类型',
  `user_display_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户的展示名',
  `org_id` varchar(50) NOT NULL DEFAULT '' COMMENT '用户所在的机构ID',
  `org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户所在的机构名称',
  `platform` varchar(50) NOT NULL DEFAULT '' COMMENT '平台信息',
  `tenant_id` varchar(50) NOT NULL DEFAULT '' COMMENT '租户ID',
  `trace_id` varchar(50) NOT NULL DEFAULT '' COMMENT '跟踪ID',
  `request_api_uri` varchar(500) NOT NULL DEFAULT '' COMMENT '请求的URI',
  `request_method` varchar(30) NOT NULL DEFAULT '' COMMENT '请求的方法',
  `input` text COMMENT '请求参数（JSON）',
  `output` text COMMENT '返回结果（JSON）',
  `success` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否执行成功：0 未成功，1 成功',
  `error` text COMMENT '错误消息',
  `time_spent` int(8) DEFAULT NULL COMMENT '耗时（单位：毫秒）',
  `client_app_id` varchar(40) NOT NULL DEFAULT '' COMMENT '客户端的应用的ID（标识是哪个应用）',
  `client_device_id` varchar(40) NOT NULL DEFAULT '' COMMENT '客户端的设备的ID',
  `client_user_agent` varchar(500) NOT NULL DEFAULT '' COMMENT '客户端的用户代理',
  `client_net_address` varchar(60) NOT NULL DEFAULT '' COMMENT '客户端的网络地址',
  `client_geo_address` varchar(60) NOT NULL DEFAULT '' COMMENT '客户端的地理地址',
  `client_geo_location` varchar(200) NOT NULL DEFAULT '' COMMENT '客户端的地理位置（经纬度JSON）',
  `server_name` varchar(40) NOT NULL DEFAULT '' COMMENT '服务器的名称',
  `server_app_name` varchar(40) NOT NULL DEFAULT '' COMMENT '服务器的应用名称',
  `data_json` text COMMENT '其他数据的JSON',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_time` (`time`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_org_id` (`org_id`) USING BTREE,
  KEY `idx_platform` (`platform`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_request_api_uri` (`request_api_uri`) USING BTREE,
  KEY `idx_success` (`success`) USING BTREE,
  KEY `idx_client_device_id` (`client_device_id`) USING BTREE,
  KEY `idx_client_net_address` (`client_net_address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='操作日志表';





CREATE TABLE `base_file` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `storage_code` varchar(50) NOT NULL COMMENT '存储配置编码（存储工具编码）',
  `bucket_name` varchar(100) NOT NULL DEFAULT '' COMMENT '桶名称',
  `origin_name` varchar(300) NOT NULL DEFAULT '' COMMENT '原始文件名',
  `size_kb` bigint(20) DEFAULT NULL COMMENT '文件大小（KB）',
  `object_key` varchar(500) NOT NULL COMMENT '存储的Key',
  `object_url` varchar(900) NOT NULL DEFAULT '' COMMENT '全路径URL',
  `metadata` text COMMENT '元数据（JSON）',
  `platform` varchar(50) NOT NULL DEFAULT '' COMMENT '平台信息',
  `tenant_id` varchar(50) NOT NULL DEFAULT '' COMMENT '租户ID',
  `owner_id` bigint(20) NOT NULL COMMENT '数据的所属人ID',
  `own_org_id` bigint(20) NOT NULL COMMENT '数据的所属机构ID',
  `create_user` bigint(20) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0 未删除，1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文件表';






