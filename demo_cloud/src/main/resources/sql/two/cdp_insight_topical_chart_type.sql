/*
Navicat MySQL Data Transfer

Source Server         : cdp-prd
Source Server Version : 50045
Source Host           : logbase.blops.com:3306
Source Database       : cdp

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2020-07-20 14:12:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cdp_insight_topical_chart_type
-- ----------------------------
DROP TABLE IF EXISTS `cdp_insight_topical_chart_type`;
CREATE TABLE `cdp_insight_topical_chart_type` (
  `insight_id` varchar(50) NOT NULL COMMENT '洞察类型id',
  `insight_title` varchar(50) NOT NULL COMMENT '洞察类型名称',
  `tagdetail_cnt` varchar(50) DEFAULT NULL COMMENT '标签数量',
  `chart_type` varchar(20) DEFAULT NULL COMMENT '洞察图表类型',
  `module_type` varchar(10) DEFAULT NULL COMMENT '模块类型',
  `module_name` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `pos_indx` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`insight_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='洞察消费统计';

-- ----------------------------
-- Records of cdp_insight_topical_chart_type
-- ----------------------------
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc1', '性别分布', '2', 'piey', '2', '会员特征', '1');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc2', '年龄分布', '6', 'piey', '2', '会员特征', '2');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc3', '生日月分布', '12', 'bary', '2', '会员特征', '3');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc4', '人群属性', '4', 'bary', '2', '会员特征', '4');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc5', '注册时间', '6', 'bary', '2', '会员特征', '5');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc6', '注册业态', '5', 'bary', '2', '会员特征', '6');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc7', '注册渠道', '17', 'bary', '2', '会员特征', '7');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('dc8', '会员等级', '4', 'bary', '2', '会员特征', '8');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('gl3', '用户类目偏好', '', 'bary', '1', '百联通概览', '3');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md1', '全业态消费次数（含停车）', '3', 'bary', '3', '客群结构', '1');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md10', '家清 消费次数', '3', 'bary', '3', '客群结构', '10');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md11', '家清 消费金额', '3', 'bary', '3', '客群结构', '11');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md12', '母婴 消费次数', '3', 'bary', '3', '客群结构', '12');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md13', '母婴 消费金额', '3', 'bary', '3', '客群结构', '13');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md14', '手机 消费次数', '3', 'bary', '3', '客群结构', '14');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md15', '手机 消费金额', '3', 'bary', '3', '客群结构', '15');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md16', '家用 消费次数', '3', 'bary', '3', '客群结构', '16');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md17', '家用 消费金额', '3', 'bary', '3', '客群结构', '17');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md18', '访问行为', '10', 'bary', '3', '客群结构', '18');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md2', '全业态消费金额(含停车)', '3', 'bary', '3', '客群结构', '2');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md3', '全业态消费金额(含停车) ', '3', 'bary', '3', '客群结构', '3');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md4', '食品 消费次数', '3', 'bary', '3', '客群结构', '4');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md5', '食品 消费金额', '3', 'bary', '3', '客群结构', '5');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md6', '美护 消费次数', '3', 'bary', '3', '客群结构', '6');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md7', '美护 消费金额', '3', 'bary', '3', '客群结构', '7');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md8', '生鲜 消费次数', '3', 'bary', '3', '客群结构', '8');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('md9', '生鲜 消费金额', '3', 'bary', '3', '客群结构', '9');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('ov1', '价值类型', '8', 'bary', '1', '百联通概览', '1');
INSERT INTO `cdp_insight_topical_chart_type` VALUES ('ov2', '周期类型', '6', 'bary', '1', '百联通概览', '2');
