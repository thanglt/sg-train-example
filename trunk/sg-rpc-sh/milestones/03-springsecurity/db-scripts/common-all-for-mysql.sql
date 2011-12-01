/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50157
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50157
File Encoding         : 65001

Date: 2011-08-04 16:41:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_common_resource`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_common_resource`;
CREATE TABLE `tbl_common_resource` (
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
-- Records of tbl_common_resource
-- ----------------------------
INSERT INTO `tbl_common_resource` VALUES ('resource001', '2011-08-04 16:15:26', null, '普通资源001', '', 'test001', '1', 'test001');
INSERT INTO `tbl_common_resource` VALUES ('resource002', '2011-08-04 16:16:30', null, '管理资源002', '', 'test002', '2', 'test002');

-- ----------------------------
-- Table structure for `tbl_common_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_common_role`;
CREATE TABLE `tbl_common_role` (
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
-- Records of tbl_common_role
-- ----------------------------
INSERT INTO `tbl_common_role` VALUES ('roleAdmin', '2011-08-04 15:55:24', null, '所有权限', '', '管理员', null, 'proj', 'ROLE_ADMIN');
INSERT INTO `tbl_common_role` VALUES ('roleUser', '2011-08-04 15:56:24', null, '普通用户', '', '一般用户', null, 'proj', 'ROLE_USER');

-- ----------------------------
-- Table structure for `tbl_common_role_tbl_common_resource`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_common_role_tbl_common_resource`;
CREATE TABLE `tbl_common_role_tbl_common_resource` (
  `roleSet_id` varchar(36) NOT NULL,
  `resourceSet_id` varchar(36) NOT NULL,
  PRIMARY KEY (`roleSet_id`,`resourceSet_id`),
  KEY `FK10BFA76B7E0626CE` (`roleSet_id`),
  KEY `FK10BFA76BB81066FE` (`resourceSet_id`),
  CONSTRAINT `FK10BFA76BB81066FE` FOREIGN KEY (`resourceSet_id`) REFERENCES `tbl_common_resource` (`id`),
  CONSTRAINT `FK10BFA76B7E0626CE` FOREIGN KEY (`roleSet_id`) REFERENCES `tbl_common_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_common_role_tbl_common_resource
-- ----------------------------
INSERT INTO `tbl_common_role_tbl_common_resource` VALUES ('roleAdmin', 'resource001');
INSERT INTO `tbl_common_role_tbl_common_resource` VALUES ('roleAdmin', 'resource002');
INSERT INTO `tbl_common_role_tbl_common_resource` VALUES ('roleUser', 'resource001');

-- ----------------------------
-- Table structure for `tbl_common_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_common_user`;
CREATE TABLE `tbl_common_user` (
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
-- Records of tbl_common_user
-- ----------------------------
INSERT INTO `tbl_common_user` VALUES ('1', '2011-08-04 15:52:06', null, null, 'asdf@email.com', '', '', '', '', null, null, null, '0', null, 'admin', 'admin', null, 'admin');
INSERT INTO `tbl_common_user` VALUES ('2', '2011-08-04 15:58:12', null, null, 'sadfsf@3.com', '', '', '', '', null, null, null, '0', null, 'user', 'user', null, 'user');

-- ----------------------------
-- Table structure for `tbl_common_user_tbl_common_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_common_user_tbl_common_role`;
CREATE TABLE `tbl_common_user_tbl_common_role` (
  `userSet_id` varchar(36) NOT NULL,
  `roleSet_id` varchar(36) NOT NULL,
  PRIMARY KEY (`userSet_id`,`roleSet_id`),
  KEY `FK5E05BBA87E0626CE` (`roleSet_id`),
  KEY `FK5E05BBA8847E69B8` (`userSet_id`),
  CONSTRAINT `FK5E05BBA8847E69B8` FOREIGN KEY (`userSet_id`) REFERENCES `tbl_common_user` (`id`),
  CONSTRAINT `FK5E05BBA87E0626CE` FOREIGN KEY (`roleSet_id`) REFERENCES `tbl_common_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_common_user_tbl_common_role
-- ----------------------------
INSERT INTO `tbl_common_user_tbl_common_role` VALUES ('1', 'roleAdmin');
INSERT INTO `tbl_common_user_tbl_common_role` VALUES ('2', 'roleUser');
