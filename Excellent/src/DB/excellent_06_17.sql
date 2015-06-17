/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : excellent

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-06-17 18:34:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(12) NOT NULL,
  `true_name` varchar(12) DEFAULT NULL,
  `pswd` varchar(15) NOT NULL,
  `phone` char(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admouzebo', '牟泽波', '123456', '18181749663');

-- ----------------------------
-- Table structure for `award`
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `refrence_id` int(8) DEFAULT NULL,
  `time` date NOT NULL,
  `level` enum('一等奖','二等奖','三等奖','优秀奖','国家级','省级','校级','院级','专业级') DEFAULT NULL,
  `award` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `comment` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `flag` bit(1) DEFAULT NULL COMMENT '0:老师 1:学生',
  PRIMARY KEY (`id`),
  KEY `index_award` (`award`),
  KEY `time_award` (`time`),
  KEY `FK_class_award` (`refrence_id`),
  CONSTRAINT `FK_class_award` FOREIGN KEY (`refrence_id`) REFERENCES `class` (`id`),
  CONSTRAINT `FK_stu_award` FOREIGN KEY (`refrence_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='flag 0-1 (0 学生,1班级)';

-- ----------------------------
-- Records of award
-- ----------------------------
INSERT INTO `award` VALUES ('1', '1', '2015-06-26', '优秀奖', '华为精英赛', '华为软件精英赛', '');

-- ----------------------------
-- Table structure for `class`
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `classNum` varchar(15) DEFAULT NULL,
  `study_model` text,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_classnum` (`classNum`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('1', '别乱动别乱动', '这个数据是傀儡数据，别动她，有用', '2015-05-19 09:56:04');
INSERT INTO `class` VALUES ('2', '计科1301', '卓越计科冲冲冲', '2015-05-18 20:41:40');
INSERT INTO `class` VALUES ('3', '计科1401', '地知道啊', '2015-05-18 20:42:01');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `new_id` int(20) DEFAULT NULL,
  `noter_id` int(8) DEFAULT NULL,
  `content` varchar(30) DEFAULT NULL,
  `up_news` bit(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_noter_id` (`noter_id`),
  KEY `FK_new_comment` (`new_id`),
  CONSTRAINT `FK_new_comment` FOREIGN KEY (`new_id`) REFERENCES `news` (`id`),
  CONSTRAINT `FK_stu_comment` FOREIGN KEY (`noter_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_teacher_comment` FOREIGN KEY (`noter_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='up_or_down 0-1 (0 down,1 up)\r\nbit 0-1 (0教师,1学生)';

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `leader` int(8) DEFAULT NULL COMMENT '组长',
  `group_name` varchar(10) DEFAULT NULL COMMENT '祖名',
  `slogan` varchar(30) DEFAULT NULL COMMENT '口号',
  `achieve` text COMMENT '成就',
  `flags` varchar(50) DEFAULT NULL COMMENT '标签',
  `tips` varchar(50) DEFAULT NULL COMMENT '寄语',
  `class_id` int(6) DEFAULT '1' COMMENT '指向class的傀儡id，非常重要',
  PRIMARY KEY (`id`),
  KEY `INDEX_GROUP_STU` (`leader`) USING BTREE,
  KEY `INDEX_CLASS_GROUP` (`group_name`) USING BTREE,
  KEY `INDEX_GROUP_CLASS` (`class_id`) USING BTREE,
  CONSTRAINT `FK_GROUP_CLASS` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_GROUP_STU` FOREIGN KEY (`leader`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', '1', '牟泽波的队', 'nononono', 'Google创始人', '帅', '长得帅不是 我的错', '2');
INSERT INTO `group` VALUES ('2', '1', '测试', 'why', '', '', '', '1');
INSERT INTO `group` VALUES ('5', '1', '测试', null, null, null, null, '1');
INSERT INTO `group` VALUES ('6', '1', '冲冲冲', '青春冲冲冲', null, '阳光', '梦想开始于脚下', '1');

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(20) DEFAULT NULL,
  `type` int(3) DEFAULT '1' COMMENT '1为普通,2为招新',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` text,
  `happen_time` date DEFAULT NULL,
  `pub_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `importance` enum('一星级','二星级','三星级','四星级','五星级') DEFAULT '一星级',
  `class_id` int(6) DEFAULT NULL,
  `attachment` varchar(50) DEFAULT NULL COMMENT '附件',
  PRIMARY KEY (`id`),
  KEY `index_pub_time` (`pub_time`),
  KEY `index_class_id` (`class_id`) USING BTREE,
  CONSTRAINT `FK_NEWS_CLASS` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('3', 'admouzebo', '1', '一万年', '爱你一万年', '2015-06-25', '2015-06-17 16:26:16', '一星级', '1', null);
INSERT INTO `news` VALUES ('4', 'admouzebo', '1', '一光 年', '程序长度一光年', '2015-06-09', '2015-06-17 16:26:11', '一星级', '2', null);
INSERT INTO `news` VALUES ('5', 'admouzebo', '2', '快来看,基友', '卓越班招新了', '2015-06-24', '2015-06-17 17:23:18', '一星级', '1', null);
INSERT INTO `news` VALUES ('6', 'admouzebo', '2', '手慢无', '卓越班招新了', '2015-06-26', '2015-06-17 17:23:16', '一星级', '1', null);

-- ----------------------------
-- Table structure for `news_extend`
-- ----------------------------
DROP TABLE IF EXISTS `news_extend`;
CREATE TABLE `news_extend` (
  `id` int(8) NOT NULL DEFAULT '0',
  `up_news` int(4) DEFAULT NULL,
  `browses` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `index_news` FOREIGN KEY (`id`) REFERENCES `news` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_extend
-- ----------------------------
INSERT INTO `news_extend` VALUES ('4', '1', '3');
INSERT INTO `news_extend` VALUES ('5', '1', '2');

-- ----------------------------
-- Table structure for `notes`
-- ----------------------------
DROP TABLE IF EXISTS `notes`;
CREATE TABLE `notes` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `noter_id` int(8) DEFAULT NULL,
  `up_note` int(7) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` bit(1) DEFAULT NULL,
  `is_passed` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FK_stu_note` (`noter_id`),
  CONSTRAINT `FK_stu_note` FOREIGN KEY (`noter_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_teacher_note` FOREIGN KEY (`noter_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='flag 0-1 (0学生留言,1教师留言)';

-- ----------------------------
-- Records of notes
-- ----------------------------
INSERT INTO `notes` VALUES ('2', '\"卓越班留言\"', null, null, '2015-05-18 20:08:50', null, '');
INSERT INTO `notes` VALUES ('3', '\"卓越班留言\"', null, null, '2015-05-18 19:38:12', null, '');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `school_id` char(14) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `true_name` varchar(12) DEFAULT NULL,
  `sex` enum('保密','女','男') DEFAULT NULL,
  `people` varchar(10) DEFAULT NULL,
  `id_card` varchar(18) DEFAULT NULL,
  `photo` varchar(20) DEFAULT NULL,
  `class_id` int(6) DEFAULT '1' COMMENT '指向傀儡classid，非常重要',
  `group_id` int(6) DEFAULT NULL,
  `height` int(7) DEFAULT NULL,
  `self_sign` varchar(30) DEFAULT NULL,
  `isPoor` bit(1) DEFAULT b'0',
  `job` varchar(30) DEFAULT NULL,
  `classJob` enum('班长','团支书','学习委员','文体委员','生活委员','普通同学') DEFAULT NULL,
  `ACMId` varchar(15) DEFAULT NULL,
  `isSingle` int(2) DEFAULT NULL,
  `loveType` varchar(100) DEFAULT NULL,
  `love_enjoy_Type` varchar(225) DEFAULT NULL,
  `other` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_scoolId` (`school_id`),
  KEY `index_sex` (`sex`),
  KEY `index_isSingle` (`isSingle`),
  KEY `index_height` (`height`),
  KEY `FK_STU_GROUP` (`group_id`),
  KEY `index_classId` (`class_id`) USING BTREE,
  CONSTRAINT `FK_STU_CLASS` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_STU_GROUP` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='singtable\r\nsex 0-2(0 保密,1 男,2 女） \r\nisPoor 0-1 (0 否';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '20123192', 'mouzebo', '牟泽波', '男', '汉族', null, null, '2', '1', '290', '帅', '', '没事做', '普通同学', '20123192', '1', '漂亮', '数据分分析', '喜欢看玩玩没想到');
INSERT INTO `student` VALUES ('4', '20123193', '机器人', '某老头', '男', '汉族', '511524199401097890', null, '2', '1', null, '帅', '', null, '团支书', null, null, null, null, null);

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `school_id` char(18) DEFAULT NULL,
  `photo` varchar(25) DEFAULT NULL,
  `name` varchar(15) NOT NULL,
  `true_name` varchar(12) DEFAULT NULL,
  `study_area` varchar(100) DEFAULT NULL,
  `lib` varchar(50) DEFAULT NULL,
  `title` enum('助教','讲师','副教授','教授') DEFAULT NULL,
  `paper` varchar(100) DEFAULT NULL,
  `class` varchar(140) DEFAULT NULL,
  `honor` varchar(140) DEFAULT NULL,
  `tips` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_shcool_id` (`school_id`),
  KEY `Index_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '201231231', null, 'temouzebo', '牟泽波', '什么都搞', '1000', '教授', '论牟泽波的重要性', null, '神级选手', '做审计选手，你也行');
