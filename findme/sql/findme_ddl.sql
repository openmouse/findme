CREATE TABLE `findme_enum_entry` (
  `enum_key` varchar(30) DEFAULT NULL COMMENT 'KEY 例如size',
  `enum_value` int(11) NOT NULL COMMENT 'value 例如40-60平方',
  `enum_num` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '类型 0-文字类型 1-数字范围类型',
  `num_begin` decimal(21,6) DEFAULT NULL COMMENT '数字类型参数开始值',
  `num_end` decimal(21,6) DEFAULT NULL COMMENT '数字类型参数结束值',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `enum_key` (`enum_key`,`enum_value`,`enum_num`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

CREATE TABLE `findme_room_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dealer` int(11) NOT NULL COMMENT '经销商',
  `room_model` int(11) DEFAULT NULL COMMENT '房型',
  `size` int(11) DEFAULT NULL COMMENT '面积',
  `style` int(11) DEFAULT NULL COMMENT '风格',
  `picture_paths` varchar(1024) DEFAULT NULL COMMENT '图片路径',
  `media_ids` varchar(512) DEFAULT NULL COMMENT '微信上传的媒体号',
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `floor_height` int(11) DEFAULT NULL COMMENT '层高',
  `function_space` int(11) DEFAULT NULL COMMENT '功能空间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `findme_room_detail_show` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `dealer` varchar(256) DEFAULT NULL COMMENT '经销商',
  `room_model` varchar(256) DEFAULT NULL COMMENT '房型',
  `size` decimal(21,6) DEFAULT NULL COMMENT '面积',
  `style` varchar(256) DEFAULT NULL COMMENT '风格',
  `picture_paths` varchar(1024) DEFAULT NULL COMMENT '图片路径',
  `floor_height` int(11) DEFAULT NULL COMMENT '层高',
  `function_space` varchar(256) DEFAULT NULL COMMENT '功能空间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='房间导入数据-用于显示';

CREATE TABLE `findme_system_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `sys_key` varchar(255) NOT NULL,
  `sys_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置';

CREATE TABLE `findme_user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime NOT NULL COMMENT '修改时间',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  KEY `username_index` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `findme_word_mapping` (
  `field` varchar(20) NOT NULL,
  `mapping_str` varchar(45) NOT NULL,
  `enum_num` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


