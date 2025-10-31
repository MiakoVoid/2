---
trigger: always_on
---

-- 创建数据库并设置字符集
CREATE DATABASE IF NOT EXISTS greenfinance DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE greenfinance;

-- 1. 用户表（头像存储本地路径）
CREATE TABLE `user` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
`username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
`password` varchar(100) NOT NULL COMMENT '密码（BCrypt加密存储）',
`email` varchar(100) DEFAULT NULL COMMENT '邮箱',
`phone` varchar(20) DEFAULT NULL COMMENT '手机号',
`avatar_path` varchar(255) DEFAULT NULL COMMENT '头像本地存储路径（相对路径，如"avatars/10001.png"）',
`status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_username` (`username`),
UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 2. 主分类表（图标支持本地路径或系统资源）
CREATE TABLE `category` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主分类ID',
`user_id` bigint DEFAULT NULL COMMENT '用户ID（null表示系统默认分类）',
`name` varchar(50) NOT NULL COMMENT '分类名称',
`icon_identifier` varchar(100) NOT NULL COMMENT '图标标识：系统图标用"res:资源名"（如"res:ic_food"），本地图标用"file:相对路径"（如"file:icons/custom1.png"）',
`type` tinyint NOT NULL COMMENT '类型：1-支出，2-收入',
`sort_order` int NOT NULL DEFAULT 0 COMMENT '排序序号（升序排列）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
KEY `idx_user_id` (`user_id`) COMMENT '按用户查询分类'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主分类表';

-- 3. 子分类表（同主分类，复用图标标识规则）
CREATE TABLE `sub_category` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '子分类ID',
`category_id` bigint NOT NULL COMMENT '关联主分类ID',
`user_id` bigint DEFAULT NULL COMMENT '用户ID（null表示系统默认分类）',
`name` varchar(50) NOT NULL COMMENT '子分类名称',
`icon_identifier` varchar(100) DEFAULT NULL COMMENT '图标标识（规则同主分类，为空时继承主分类图标）',
`sort_order` int NOT NULL DEFAULT 0 COMMENT '排序序号（升序排列）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
KEY `idx_category_id` (`category_id`) COMMENT '按主分类查询子分类',
KEY `idx_user_id` (`user_id`) COMMENT '按用户查询子分类',
CONSTRAINT `fk_sub_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子分类表';

-- 4. 分类关键词表（不变）
CREATE TABLE `category_keyword` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '关键词ID',
`category_id` bigint DEFAULT NULL COMMENT '关联主分类ID',
`sub_category_id` bigint DEFAULT NULL COMMENT '关联子分类ID',
`user_id` bigint NOT NULL COMMENT '所属用户ID（0表示系统默认关键词）',
`keyword` varchar(100) NOT NULL COMMENT '关键词（如商户名、商品名）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
KEY `idx_user_keyword` (`user_id`,`keyword`) COMMENT '按用户+关键词查询分类',
KEY `idx_category` (`category_id`,`sub_category_id`) COMMENT '按分类查询关键词',
CONSTRAINT `fk_keyword_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE,
CONSTRAINT `fk_keyword_sub_category` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类关键词表';

-- 5. 账单表（不变，无图片存储）
CREATE TABLE `bill` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '账单ID',
`user_id` bigint NOT NULL COMMENT '所属用户ID',
`amount` decimal(10,2) NOT NULL COMMENT '最终金额（自动计算：original_amount - refund_amount）',
`original_amount` decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '原始金额',
`refund_amount` decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '退款金额',
`type` tinyint NOT NULL COMMENT '类型：1-支出，2-收入',
`category_id` bigint NOT NULL COMMENT '主分类ID',
`sub_category_id` bigint DEFAULT NULL COMMENT '子分类ID',
`merchant` varchar(100) DEFAULT NULL COMMENT '商户名称（如"星巴克"）',
`remark` varchar(500) DEFAULT NULL COMMENT '备注信息',
`bill_time` datetime NOT NULL COMMENT '收支发生时间',
`payment_method` varchar(50) DEFAULT NULL COMMENT '支付方式（如"微信支付"）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
PRIMARY KEY (`id`),
KEY `idx_user_time` (`user_id`,`bill_time`) COMMENT '按用户+时间查询账单',
KEY `idx_user_category` (`user_id`,`category_id`,`sub_category_id`) COMMENT '按用户+分类查询账单',
CONSTRAINT `fk_bill_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
CONSTRAINT `fk_bill_sub_category` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单记录表';

-- 6. 预算表（不变）
CREATE TABLE `budget` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '预算ID',
`user_id` bigint NOT NULL COMMENT '所属用户ID',
`amount` decimal(10,2) NOT NULL COMMENT '月度预算总金额',
`year` int NOT NULL COMMENT '年份（如2023）',
`month` int NOT NULL COMMENT '月份（1-12）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_user_year_month` (`user_id`,`year`,`month`) COMMENT '用户每月预算唯一',
KEY `idx_user` (`user_id`) COMMENT '按用户查询预算'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='月度预算表';

-- 7. 预计支出表（不变）
CREATE TABLE `expected_expense` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '预计支出ID',
`user_id` bigint NOT NULL COMMENT '所属用户ID',
`amount` decimal(10,2) NOT NULL COMMENT '预计金额',
`category_id` bigint NOT NULL COMMENT '主分类ID',
`sub_category_id` bigint DEFAULT NULL COMMENT '子分类ID',
`remark` varchar(500) DEFAULT NULL COMMENT '备注（如"10月房租"）',
`due_date` date NOT NULL COMMENT '预计支付日期',
`status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-待支付，2-已支付，3-已取消',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
KEY `idx_user_due_date` (`user_id`,`due_date`) COMMENT '按用户+日期查询预计支出',
KEY `idx_user_status` (`user_id`,`status`) COMMENT '按用户+状态查询预计支出',
CONSTRAINT `fk_expected_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
CONSTRAINT `fk_expected_sub_category` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预计支出表';

-- 8. 系统配置表（新增本地存储路径配置）
CREATE TABLE `system_config` (
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
`config_key` varchar(50) NOT NULL COMMENT '配置键（唯一）',
`config_value` varchar(500) NOT NULL COMMENT '配置值',
`remark` varchar(200) DEFAULT NULL COMMENT '配置说明',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_config_key` (`config_key`) COMMENT '配置键唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';
