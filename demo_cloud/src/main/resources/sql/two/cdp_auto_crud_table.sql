/*
Navicat MySQL Data Transfer

Source Server         : sit_cdp
Source Server Version : 50725
Source Host           : mysql57.st.iblidc.com:3306
Source Database       : cdp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-08-05 15:46:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cdp_auto_crud_table
-- ----------------------------
DROP TABLE IF EXISTS `cdp_auto_crud_table`;
CREATE TABLE `cdp_auto_crud_table` (
  `code` bigint(255) NOT NULL COMMENT '编码',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `un_key1` varchar(255) NOT NULL COMMENT '唯一键1',
  `un_key2` varchar(255) NOT NULL COMMENT '唯一键2',
  `c_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`,`name`),
  UNIQUE KEY `un_cdp_auto_crud_table` (`un_key1`,`un_key2`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自动crud测试表';

-- ----------------------------
-- Records of cdp_auto_crud_table
-- ----------------------------
INSERT INTO `cdp_auto_crud_table` VALUES ('1', 'w', '11', '22', '2020-08-03 14:44:26');
INSERT INTO `cdp_auto_crud_table` VALUES ('1', 'w1', '2323', '22', '2020-08-03 14:44:26');
INSERT INTO `cdp_auto_crud_table` VALUES ('1', 'w2', '12', '23', '2020-08-04 14:44:41');
INSERT INTO `cdp_auto_crud_table` VALUES ('3', 'w3', '13', '24', null);
