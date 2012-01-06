/*
MySQL Backup
Source Server Version: 5.5.16
Source Database: sg-sh
Date: 2012-1-6 16:49:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `tbl_comm_res`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comm_res`;
CREATE TABLE `tbl_comm_res` (
  `id` varchar(36) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `description` text,
  `isSystem` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `seq` int(11) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_comm_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comm_role`;
CREATE TABLE `tbl_comm_role` (
  `id` varchar(36) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `description` text,
  `isSystem` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `ouId` varchar(32) DEFAULT NULL,
  `projectId` varchar(32) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_comm_role_tbl_comm_res`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comm_role_tbl_comm_res`;
CREATE TABLE `tbl_comm_role_tbl_comm_res` (
  `roleSet_id` varchar(36) NOT NULL,
  `resourceSet_id` varchar(36) NOT NULL,
  PRIMARY KEY (`roleSet_id`,`resourceSet_id`),
  KEY `FK97BE08A57E0626CE` (`roleSet_id`),
  KEY `FK97BE08A5B81066FE` (`resourceSet_id`),
  CONSTRAINT `FK97BE08A5B81066FE` FOREIGN KEY (`resourceSet_id`) REFERENCES `tbl_comm_res` (`id`),
  CONSTRAINT `FK97BE08A57E0626CE` FOREIGN KEY (`roleSet_id`) REFERENCES `tbl_comm_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_comm_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comm_user`;
CREATE TABLE `tbl_comm_user` (
  `id` varchar(36) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `isAccountEnabled` bit(1) NOT NULL,
  `isAccountExpired` bit(1) NOT NULL,
  `isAccountLocked` bit(1) NOT NULL,
  `isCredentialsExpired` bit(1) NOT NULL,
  `lastUpdateDate` datetime DEFAULT NULL,
  `lockedDate` datetime DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `loginFailureCount` int(11) NOT NULL,
  `loginIp` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `projId` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_comm_user_tbl_comm_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comm_user_tbl_comm_role`;
CREATE TABLE `tbl_comm_user_tbl_comm_role` (
  `userSet_id` varchar(36) NOT NULL,
  `roleSet_id` varchar(36) NOT NULL,
  PRIMARY KEY (`userSet_id`,`roleSet_id`),
  KEY `FK5587A3A67E0626CE` (`roleSet_id`),
  KEY `FK5587A3A6847E69B8` (`userSet_id`),
  CONSTRAINT `FK5587A3A6847E69B8` FOREIGN KEY (`userSet_id`) REFERENCES `tbl_comm_user` (`id`),
  CONSTRAINT `FK5587A3A67E0626CE` FOREIGN KEY (`roleSet_id`) REFERENCES `tbl_comm_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_sales_menu_node`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sales_menu_node`;
CREATE TABLE `tbl_sales_menu_node` (
  `id` varchar(36) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `isFolder` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `viewId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_sales_order`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sales_order`;
CREATE TABLE `tbl_sales_order` (
  `id` varchar(36) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `tbl_comm_res` VALUES ('resource001','2011-08-04 16:15:26',NULL,'普通资源001','','test001','1','test001'), ('resource002','2011-08-04 16:16:30',NULL,'管理资源002','','test002','2','test002');
INSERT INTO `tbl_comm_role` VALUES ('roleAdmin','2011-08-04 15:55:24',NULL,'所有权限','','管理员',NULL,'proj','ROLE_ADMIN'), ('roleUser','2011-08-04 15:56:24',NULL,'普通用户','\0','一般用户',NULL,'proj','ROLE_USER');
INSERT INTO `tbl_comm_role_tbl_comm_res` VALUES ('roleAdmin','resource001'), ('roleAdmin','resource002'), ('roleUser','resource001');
INSERT INTO `tbl_comm_user` VALUES ('1','2011-08-04 15:52:06','2012-01-06 16:47:53',NULL,'asdf@email.com','','\0','\0','\0',NULL,NULL,'2012-01-06 16:47:53','0','127.0.0.1','admin','admin',NULL,'admin'), ('2','2011-08-04 15:58:12','2012-01-06 16:42:24',NULL,'sadfsf@3.com','','\0','\0','\0',NULL,NULL,'2012-01-06 16:42:24','0','127.0.0.1','user','user',NULL,'user');
INSERT INTO `tbl_comm_user_tbl_comm_role` VALUES ('1','roleAdmin'), ('2','roleUser');
INSERT INTO `tbl_sales_menu_node` VALUES ('0982a0c5-3b5a-45d2-b930-dc42fbaa6d00','2012-01-06 16:33:18','2012-01-06 16:33:18','testTree','1','icons/bullet_go.png','Rogine Leger','6e0ad168-55de-47ae-8d21-81758fa24f58',NULL,NULL), ('0a7cee6d-a49d-4970-a861-8e8204d85d81','2012-01-06 16:34:49','2012-01-06 16:34:49','userManage','1','icons/key.png','权限管理','root',NULL,NULL), ('0dbe6745-a39d-4154-8fc1-e70f9b7bcf13','2012-01-06 16:34:49','2012-01-06 16:34:49','userManage','0','icons/user.png','角色管理','0a7cee6d-a49d-4970-a861-8e8204d85d81','2','roleManageView'), ('2c38eb11-e017-4e91-b889-995e1961b9f3','2012-01-06 16:33:18','2012-01-06 16:33:18','testTree','0','icons/clock.png','Cheryl Pearson','710ec15f-7aff-4a1b-8e5a-e2243e216a24',NULL,NULL), ('317ba15c-2de1-4f83-a518-64343147d4f9','2012-01-06 16:34:49','2012-01-06 16:34:49','userManage','0','icons/user_gray.png','用户管理','0a7cee6d-a49d-4970-a861-8e8204d85d81','1','userManageView'), ('376a5c87-86ac-4797-b68a-5d0e93bd667b','2012-01-06 16:34:49','2012-01-06 16:34:49','userManage','0','icons/brick_go.png','分配资源给角色','0a7cee6d-a49d-4970-a861-8e8204d85d81','5',''), ('3da30de9-6a71-4e90-ba75-226a3a16fd89','2012-01-06 16:33:18','2012-01-06 16:33:18','testTree','0','icons/anchor.png','Olivier Doucet','710ec15f-7aff-4a1b-8e5a-e2243e216a24',NULL,NULL), ('4a4e5fc8-cfc3-423c-8c2a-f8894fe72fda','2012-01-06 16:34:49','2012-01-06 16:34:49','userManage','0','icons/brick.png','资源管理','0a7cee6d-a49d-4970-a861-8e8204d85d81','4',NULL), ('6e0ad168-55de-47ae-8d21-81758fa24f58','2012-01-06 16:33:18','2012-01-06 16:33:18','testTree','1','icons/bullet_go.png','Charles Madigen','root',NULL,NULL), ('710ec15f-7aff-4a1b-8e5a-e2243e216a24','2012-01-06 16:33:18','2012-01-06 16:33:18','testTree','1','icons/bullet_go.png','Gene Porter','6e0ad168-55de-47ae-8d21-81758fa24f58',NULL,NULL), ('e7757c62-ba57-436f-b74c-4182071ca27c','2012-01-06 16:34:49','2012-01-06 16:34:49','userManage','0','icons/user_go.png','分配角色给用户','0a7cee6d-a49d-4970-a861-8e8204d85d81','3','assignRole2User');
