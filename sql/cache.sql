

DROP TABLE IF EXISTS `t_cache`;
CREATE TABLE `t_cache` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(300) NOT NULL COMMENT '缓存名称',
  `key` varchar(600) NOT NULL COMMENT '缓存的KEY',
  `value` text  NULL COMMENT '缓存的值',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间（为空则永不过期）',
  `modify_time_db` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '数据库更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE,
  INDEX `idx_key`(`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='缓存数据表';




