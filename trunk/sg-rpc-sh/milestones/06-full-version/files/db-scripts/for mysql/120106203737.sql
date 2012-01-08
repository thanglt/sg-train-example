/*
MySQL Backup
Source Server Version: 5.1.47
Source Database: sg-sh
Date: 2012/1/6 20:37:37
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
  CONSTRAINT `FK97BE08A57E0626CE` FOREIGN KEY (`roleSet_id`) REFERENCES `tbl_comm_role` (`id`),
  CONSTRAINT `FK97BE08A5B81066FE` FOREIGN KEY (`resourceSet_id`) REFERENCES `tbl_comm_res` (`id`)
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
  CONSTRAINT `FK5587A3A67E0626CE` FOREIGN KEY (`roleSet_id`) REFERENCES `tbl_comm_role` (`id`),
  CONSTRAINT `FK5587A3A6847E69B8` FOREIGN KEY (`userSet_id`) REFERENCES `tbl_comm_user` (`id`)
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
INSERT INTO `tbl_comm_user` VALUES ('1','2011-08-04 15:52:06','2012-01-06 20:36:33',NULL,'asdf@email.com','','\0','\0','\0',NULL,NULL,'2012-01-06 20:36:33','0','127.0.0.1','admin','admin',NULL,'admin'), ('2','2011-08-04 15:58:12','2012-01-06 20:36:54',NULL,'sadfsf@3.com','','\0','\0','\0',NULL,NULL,'2012-01-06 20:36:54','0','127.0.0.1','user','user',NULL,'user');
INSERT INTO `tbl_comm_user_tbl_comm_role` VALUES ('1','roleAdmin'), ('1','roleUser'), ('2','roleUser');
INSERT INTO `tbl_sales_menu_node` VALUES ('2a822c02-4c38-4a8d-9c06-1d12a2a81af9','2011-09-01 20:53:46','2011-09-01 20:53:46','testTree','1','icons/bullet_go.png','Charles Madigen','root',NULL,NULL), ('3864770c-0f85-4912-86c6-3c3c38a7cd1c','2011-09-01 20:53:54','2011-09-01 20:53:54','testTree2','0','icons/application_xp_terminal.png','Test App 1','d8a810ea-630c-464a-b513-31e1c17187d7',NULL,'orderViewOne'), ('3d1d88a7-2e01-4cf7-8301-3a5a77b85145','2011-09-01 20:53:54','2011-09-01 20:53:54','testTree2','0','icons/asterisk_orange.png','Test App 3','d8a810ea-630c-464a-b513-31e1c17187d7',NULL,NULL), ('3ecbd6dd-7953-4678-8d0b-65b966735b14','2011-09-01 20:54:01','2011-09-01 20:54:01','userManage','1','icons/key.png','权限管理','root',NULL,NULL), ('47f7701b-fe18-470d-8072-ed7c09597934','2011-09-01 20:53:46','2011-09-01 20:53:46','testTree','0','icons/anchor.png','Olivier Doucet','c0922dbd-6ab0-4be4-9dfd-32a94d85075d',NULL,NULL), ('4c47e660-db99-438e-a9f6-caabc3518620','2011-09-01 20:54:01','2011-09-01 20:54:01','userManage','0','icons/user.png','角色管理','3ecbd6dd-7953-4678-8d0b-65b966735b14','2','roleManageView'), ('6cdc0094-14e4-47c0-bc84-0ccabf07db04','2011-09-01 20:54:01','2011-09-01 20:54:01','userManage','0','icons/brick_go.png','分配资源给角色','3ecbd6dd-7953-4678-8d0b-65b966735b14','5',NULL), ('7235b544-ad76-43fb-a07c-f4fb048ef5bb','2011-09-01 20:53:54','2011-09-01 20:53:54','testTree2','0','icons/bug.png','Test App 2','d8a810ea-630c-464a-b513-31e1c17187d7',NULL,'orderViewTwo'), ('7d65fa0c-de7c-4866-9a18-bbb333da1ff4','2011-09-01 20:53:46','2011-09-01 20:53:46','testTree','0','icons/clock.png','Cheryl Pearson','c0922dbd-6ab0-4be4-9dfd-32a94d85075d',NULL,NULL), ('8dcf2b5a-58d4-40fb-a730-0a9b6b395a68','2011-09-01 20:54:01','2011-09-01 20:54:01','userManage','0','icons/user_go.png','分配角色给用户','3ecbd6dd-7953-4678-8d0b-65b966735b14','3','assignRole2User'), ('bba72ff1-c76a-4708-8a65-82c2500a6940','2011-09-01 20:54:01','2011-09-01 20:54:01','userManage','0','icons/user_gray.png','用户管理','3ecbd6dd-7953-4678-8d0b-65b966735b14','1','userManageView'), ('bf264de3-bb0e-40e5-86ee-d58d6058f86d','2011-09-01 20:53:46','2011-09-01 20:53:46','testTree','1','icons/bullet_go.png','Rogine Leger','2a822c02-4c38-4a8d-9c06-1d12a2a81af9',NULL,NULL), ('c0922dbd-6ab0-4be4-9dfd-32a94d85075d','2011-09-01 20:53:46','2011-09-01 20:53:46','testTree','1','icons/bullet_go.png','Gene Porter','2a822c02-4c38-4a8d-9c06-1d12a2a81af9',NULL,NULL), ('d8a810ea-630c-464a-b513-31e1c17187d7','2011-09-01 20:53:54','2011-09-01 20:53:54','testTree2','1','icons/bullet_go.png','Test Root','root',NULL,NULL), ('e6cfe741-bcf7-447b-a63b-00dcf996b06d','2011-09-01 20:54:01','2011-09-01 20:54:01','userManage','0','icons/brick.png','资源管理','3ecbd6dd-7953-4678-8d0b-65b966735b14','4',NULL);
INSERT INTO `tbl_sales_order` VALUES ('0b95a692-9a9c-42b2-a50d-fed8691365ce','2011-09-02 22:14:48','2011-09-02 22:14:48','123','123'), ('0c4e43c0-31cc-4ff8-a0a1-855c6ef06205','2011-09-01 22:31:16','2011-09-01 22:31:16','111','2222'), ('a02b8c89-10aa-4a6a-9adf-41480c15debd','2011-09-01 21:00:30','2011-09-01 21:00:30','asdsad','asd');
