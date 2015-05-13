/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : excellent

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-05-13 19:47:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(12) NOT NULL,
  `phone` char(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for `award`
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `refrence_id` int(8) DEFAULT NULL,
  `time` date NOT NULL,
  `level` enum('一等奖','二等奖','三等奖','优秀奖','国家级','省级','校级','院级','专业级') DEFAULT NULL,
  `award` varbinary(100) DEFAULT NULL,
  `comment` varbinary(225) DEFAULT NULL,
  `flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_award` (`award`),
  KEY `time_award` (`time`),
  KEY `FK_class_award` (`refrence_id`),
  CONSTRAINT `FK_class_award` FOREIGN KEY (`refrence_id`) REFERENCES `class` (`id`),
  CONSTRAINT `FK_stu_award` FOREIGN KEY (`refrence_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='flag 0-1 (0 学生,1班级)';

-- ----------------------------
-- Records of award
-- ----------------------------

-- ----------------------------
-- Table structure for `class`
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `classNum` varchar(15) DEFAULT NULL,
  `培养模式` text,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_classnum` (`classNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------

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
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `title` varbinary(30) DEFAULT NULL,
  `content` text,
  `happen_time` date DEFAULT NULL,
  `pub_time` datetime DEFAULT NULL,
  `importance` enum('一星级','二星级','三星级','四星级','五星级') DEFAULT NULL,
  `up_news` int(7) DEFAULT NULL,
  `classNum` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_pub_time` (`pub_time`),
  KEY `Index_class_num` (`classNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for `notes`
-- ----------------------------
DROP TABLE IF EXISTS `notes`;
CREATE TABLE `notes` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` varbinary(225) DEFAULT NULL,
  `noter_id` int(8) DEFAULT NULL,
  `up_note` int(7) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_stu_note` (`noter_id`),
  CONSTRAINT `FK_stu_note` FOREIGN KEY (`noter_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_teacher_note` FOREIGN KEY (`noter_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='flag 0-1 (0学生留言,1教师留言)';

-- ----------------------------
-- Records of notes
-- ----------------------------

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `schoolId` char(14) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `people` varchar(10) DEFAULT NULL,
  `idCard` varchar(18) DEFAULT NULL,
  `photo` varchar(20) DEFAULT NULL,
  `height` int(7) DEFAULT NULL,
  `selfSign` varchar(30) DEFAULT NULL,
  `isPoor` bit(1) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `classJob` enum('班长','团支书','学习委员','文体委员','生活委员','普通同学') DEFAULT NULL,
  `ACMId` varchar(15) DEFAULT NULL,
  `isSingle` int(2) DEFAULT NULL,
  `loveType` varchar(100) DEFAULT NULL,
  `love_enjoy_Type` varchar(225) DEFAULT NULL,
  `other` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_scoolId` (`schoolId`),
  KEY `index_sex` (`sex`),
  KEY `index_isSingle` (`isSingle`),
  KEY `index_height` (`height`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='singtable\r\nsex 0-2(0 保密,1 男,2 女） \r\nisPoor 0-1 (0 否';

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `school_id` char(18) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
