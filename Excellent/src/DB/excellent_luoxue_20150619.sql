/*
Navicat MySQL Data Transfer

Source Server         : lx
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : excellent

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-06-19 22:41:23
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('1', '软件卓越0901', '', '2015-06-18 15:38:47');
INSERT INTO `class` VALUES ('2', '软件卓越1001', '', '2015-06-18 15:38:49');
INSERT INTO `class` VALUES ('3', '软件卓越1101', '', '2015-06-18 15:37:03');
INSERT INTO `class` VALUES ('4', '软件卓越1201', null, '2015-06-18 15:38:14');
INSERT INTO `class` VALUES ('5', '软件卓越1301', null, '2015-06-18 15:38:26');
INSERT INTO `class` VALUES ('6', '计科卓越1301', null, '2015-06-18 15:38:34');
INSERT INTO `class` VALUES ('7', '软件卓越1205', '', '2015-06-19 13:28:09');

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
  CONSTRAINT `FK_GROUP_CLASS` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_GROUP_STU` FOREIGN KEY (`leader`) REFERENCES `student` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', '1', '第四组', 'nononono', 'Google创始人', '帅', '长得帅不是 我的错', '2');
INSERT INTO `group` VALUES ('2', '1', '第三组', 'why', '', '', '', '2');
INSERT INTO `group` VALUES ('5', '1', '第二组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('6', '1', '第一组', '青春冲冲冲', null, '阳光', '梦想开始于脚下', '2');
INSERT INTO `group` VALUES ('7', '1', '未分组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('8', null, '未分组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('9', null, '第一组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('10', null, '第二组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('11', null, '第三组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('12', null, '第四组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('13', null, '第五组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('14', null, '第六组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('15', null, '第七组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('16', null, '第八组', null, null, null, null, '6');
INSERT INTO `group` VALUES ('17', null, '第八组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('18', null, '第七组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('19', null, '第六组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('20', null, '第五组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('21', null, '第四组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('22', null, '第三组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('23', null, '第二组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('24', null, '第一组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('25', null, '未分组', null, null, null, null, '2');
INSERT INTO `group` VALUES ('26', null, '未分组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('28', null, '第一组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('29', null, '第二组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('30', null, '第三组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('31', null, '第四组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('32', null, '第五组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('33', null, '第六组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('34', null, '第七组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('35', null, '第八组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('36', null, '第八组', null, null, null, null, '4');
INSERT INTO `group` VALUES ('37', null, '第八组', null, null, null, null, '7');
INSERT INTO `group` VALUES ('38', null, '第八组', null, null, null, null, '5');
INSERT INTO `group` VALUES ('39', null, '第八组', null, null, null, null, null);
INSERT INTO `group` VALUES ('40', null, '第七组', null, null, null, null, null);
INSERT INTO `group` VALUES ('41', null, '第七组', null, null, null, null, '5');
INSERT INTO `group` VALUES ('42', null, '第七组', null, null, null, null, '4');
INSERT INTO `group` VALUES ('43', null, '第七组', null, null, null, null, '3');
INSERT INTO `group` VALUES ('44', null, '第六组', null, null, null, null, '4');
INSERT INTO `group` VALUES ('45', null, '第六组', null, null, null, null, '5');
INSERT INTO `group` VALUES ('46', null, '第五组', null, null, null, null, '4');
INSERT INTO `group` VALUES ('47', null, '第五组', null, null, null, null, '5');

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('21', null, '1', '发布班级动态测试5', '<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	计算机科学与技术学院卓越软件工程师实验班是为贯彻落实《国家中长期教育改革和发展规划纲要（2010-2020年）》和《国家中长期人才发展规划纲要（2010-2020年）》，进一步挖掘学生的科技创新潜力，开阔学生视野，提高学生的创新能力和综合素质，培养具有创新精神的高素质人才而开设的，第一届卓越软件工程师实验班已在学院2009级本科中招生。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	<span style=\"font-weight:700;\">培养模式</span>\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	实验班采用“3+1”培养模式（三年在学校进行学习，最后一年在企业进行联合培养），根据双向选择的原则，为每名进入实验班的学生配备一名指导教师（以下简称导师），着重培养学生的个性发展以及实践和创新意识，更深层次的挖掘学生才能，使学生能够全面均衡的发展，培养满足企业需求的软件开发工程师。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	<span style=\"font-weight:700;\">学生选拔方式</span>\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	每年选拔30名，选拔工作秉承公开、公平、公正的原则，采取学生自愿报名、按学生成绩和综合素质排名进行初审、再经过实践能力测试和面试，初步人选确定并公示。最终名单经学校教务处审批通过后，学院发放“计算机科学与技术学院卓越软件工程师实验班”录取通知。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	<span style=\"font-weight:700;\">教学模式</span>\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	培养方案中的学科基础、专业基础和专业方向方面的课程，在注重理论基础知识学习的同时，强调学生的实践能力培养，在联合培养阶段，由企业选派的导师和学院导师共同对培养方案中当年度的必修课和部分选修课采取学生参与企业方的实际项目进行置换，指导学生完成相关课程的学习。学生前3学年在学校完成规定学分学习后，进人学院与企业联合开展为期半年或一年的实习。该实习将结合企业实际项目，由企业方指定人员担任学生的项目指导教师，对学生进行指导和考评。根据生产实际需要，由学生、企业和学生导师协商确定学生毕业设计题目，由企业和学院双方导师对学生进行毕业设计指导和考核工作。同时将程序综合设计、信息系统和软件测试、接口技术和攻防对抗五门课程通过暑期项目实践训练的 形式授课。该班学生在校学习期间需要参与导师的科研项目，通过项目训练提升学生的团队协作、交流沟通、语言表达等综合能力。\n</p>', '2015-06-06', '2015-06-19 21:23:28', '一星级', '2', null);
INSERT INTO `news` VALUES ('22', null, '1', '发布班级动态测试5ggghyrtyutyyffff', '<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	计算机科学与技术学院卓越软件工程师实验班是为贯彻落实《国家中长期教育改革和发展规划纲要（2010-2020年）》和《国家中长期人才发展规划纲要（2010-2020年）》，进一步挖掘学生的科技创新潜力，开阔学生视野，提高学生的创新能力和综合素质，培养具有创新精神的高素质人才而开设的，第一届卓越软件工程师实验班已在学院2009级本科中招生。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	<span style=\"font-weight:700;\">培养模式</span>\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	实验班采用“3+1”培养模式（三年在学校进行学习，最后一年在企业进行联合培养），根据双向选择的原则，为每名进入实验班的学生配备一名指导教师（以下简称导师），着重培养学生的个性发展以及实践和创新意识，更深层次的挖掘学生才能，使学生能够全面均衡的发展，培养满足企业需求的软件开发工程师。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	<span style=\"font-weight:700;\">学生选拔方式</span>\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	每年选拔30名，选拔工作秉承公开、公平、公正的原则，采取学生自愿报名、按学生成绩和综合素质排名进行初审、再经过实践能力测试和面试，初步人选确定并公示。最终名单经学校教务处审批通过后，学院发放“计算机科学与技术学院卓越软件工程师实验班”录取通知。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	<span style=\"font-weight:700;\">教学模式</span>\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	培养方案中的学科基础、专业基础和专业方向方面的课程，在注重理论基础知识学习的同时，强调学生的实践能力培养，在联合培养阶段，由企业选派的导师和学院导师共同对培养方案中当年度的必修课和部分选修课采取学生参与企业方的实际项目进行置换，指导学生完成相关课程的学习。学生前3学年在学校完成规定学分学习后，进人学院与企业联合开展为期半年或一年的实习。该实习将结合企业实际项目，由企业方指定人员担任学生的项目指导教师，对学生进行指导和考评。根据生产实际需要，由学生、企业和学生导师协商确定学生毕业设计题目，由企业和学院双方导师对学生进行毕业设计指导和考核工作。同时将程序综合设计、信息系统和软件测试、接口技术和攻防对抗五门课程通过暑期项目实践训练的 形式授课。该班学生在校学习期间需要参与导师的科研项目，通过项目训练提升学生的团队协作、交流沟通、语言表达等综合能力。\n</p>', '2015-06-06', '2015-06-19 21:25:11', '一星级', '2', null);
INSERT INTO `news` VALUES ('23', null, '1', '发布班级动态测试6', '<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	以社会需求为导向，在学校“共建与产学研联合办学”思想指导下，与合作单位联合培养坚持社会主义方向，具有良好的人文道德情操、工程职业道德，具有扎实的工程理论基础和宽广的知识面，熟练掌握软件开发的核心技能，能综合运用专业知识分析和解决实际软件工程问题，具有科技创新精神，能依据工程需要自主学习并优化自身的知识体系，拥有优良的团队协作能力、社会交往与组织管理能力，适应工程团队协作开发需求，成为人格健全、能力突出、吃苦耐劳、综合素质优秀的卓越工程人才。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	采用“3+1”校企联合培养模式，把工程师培养分为“3年”校内学习和“1年”企业学习两个培养阶段，前3年学校主导、企业参与，后一年学生进入企业参与实际工程项目和设计；毕业后获得学士学位，成为卓越的软件工程人才。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	该模式在二年级学生中选拔优秀学生进入试点班，前3年学生在校完成规定学分学习，第4年学生进入企业开展毕业实习，采用跟班、岗位锻炼等方式学习本行业的生产与管理流程，由企业方担任学生实习的指导与考评工作。根据生产实际需要，由学生、企业和学校三方协商确定学生毕业设计课题，由校企双方共同承担对学生毕业设计（论文）的指导，并由校企双方组成的答辩委员会对学生的毕业设计（论文）质量以审阅、质疑等形式进行评价。\n</p>\n<p style=\"text-indent:2em;color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;font-size:14px;background-color:#FFFFFF;\">\n	卓越班制定个性化培养方案，实行“双导师制”，由学校教师和企业工程师共同完成理论教学、实践教学任务。学科基础和专业特色课程将采用理论与实际紧密结合、具有工程实践特色的教材和教学大纲，结合工程项目和课题进行实践训练。\n</p>\n<img src=\"/Excellent/pages/images/150619212557.jpg\" alt=\"\" />', '2015-06-07', '2015-06-19 21:25:59', '一星级', '6', null);
INSERT INTO `news` VALUES ('24', null, '1', '发布班级动态测试1-表格测试', '<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\n	<tbody>\n		<tr>\n			<td>\n				和黑嘿嘿\n			</td>\n			<td>\n				哈就不吃卡萨的成本烦恼卡萨丁费v搞那么久vsndfjk\n			</td>\n		</tr>\n		<tr>\n			<td>\n				出色的人\n			</td>\n			<td>\n				vedrfge\n			</td>\n		</tr>\n		<tr>\n			<td>\n				别人桃花源记\n			</td>\n			<td>\n				brtjuk7\n			</td>\n		</tr>\n	</tbody>\n</table>\n<br />\n<span id=\"__kindeditor_bookmark_start_0__\"></span>', '2015-06-19', '2015-06-19 21:28:53', '一星级', '3', null);

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
INSERT INTO `news_extend` VALUES ('21', '0', '19');
INSERT INTO `news_extend` VALUES ('22', '0', '5');
INSERT INTO `news_extend` VALUES ('23', '0', '3');
INSERT INTO `news_extend` VALUES ('24', '0', '3');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='flag 0-1 (0学生留言,1教师留言)';

-- ----------------------------
-- Records of notes
-- ----------------------------
INSERT INTO `notes` VALUES ('2', '\"卓越班留言\"', null, null, '2015-05-18 20:08:50', null, '');
INSERT INTO `notes` VALUES ('4', '阿里巴巴-技术质量部内推开始啦，大家抓紧时间咯……', null, null, '2015-06-18 21:39:02', null, '');
INSERT INTO `notes` VALUES ('5', '落霞与孤鹜齐飞，秋水共长天一色！', null, null, '2015-06-18 21:43:48', null, '');

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
  CONSTRAINT `FK_STU_CLASS` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_STU_GROUP` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='singtable\r\nsex 0-2(0 保密,1 男,2 女） \r\nisPoor 0-1 (0 否';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '20123192', 'mouzebo', '牟泽波', '男', '汉族', null, null, '2', '1', '290', '帅', '', '没事做', '普通同学', '20123192', '1', '漂亮', '数据分分析', '喜欢看玩玩没想到');
INSERT INTO `student` VALUES ('4', '20123193', '机器人', '某老头', '男', '汉族', '511524199401097890', null, '2', '1', null, '帅', '', null, '团支书', null, null, null, null, null);
INSERT INTO `student` VALUES ('5', '20121179', null, '罗雪', null, null, null, null, '6', '8', null, null, '', null, null, null, null, null, null, '暂无');
INSERT INTO `student` VALUES ('6', '201211791', null, '罗雪1', null, null, null, null, '6', '8', null, null, '', null, null, null, null, null, null, '暂无');
INSERT INTO `student` VALUES ('7', '201211791', null, '罗雪2', null, null, null, null, '4', '46', null, null, '', null, null, null, null, null, null, '开朗活泼');
INSERT INTO `student` VALUES ('8', '201211795', null, '罗雪5', null, null, null, null, '4', '46', null, null, '', null, null, null, null, null, null, '开朗活泼，乐于助人');
INSERT INTO `student` VALUES ('10', '201211795', null, '小小', null, null, null, null, '6', '9', null, null, '', null, null, null, null, null, null, '开朗活泼，乐于助人');
INSERT INTO `student` VALUES ('11', '201211795', null, '田躲躲', null, null, null, null, '6', '10', null, null, '', null, null, null, null, null, null, '开朗活泼，乐于助人');
INSERT INTO `student` VALUES ('12', '201211795', null, '田躲躲1', null, null, null, null, '6', '8', null, null, '', null, null, null, null, null, null, '开朗活泼，乐于助人');

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
  `study_area` varchar(1000) DEFAULT NULL,
  `lib` varchar(50) DEFAULT NULL,
  `title` enum('助教','讲师','副教授','教授') DEFAULT NULL,
  `paper` varchar(100) DEFAULT NULL,
  `class` varchar(140) DEFAULT NULL,
  `honor` varchar(140) DEFAULT NULL,
  `tips` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_shcool_id` (`school_id`),
  KEY `Index_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '201231231', 'images/fanyong.png', '范  勇 ', '范勇', '教授，博士，硕士生导师，学术带头人，四川省计算机学会多媒体专委员会委员。主要研究方向为机器视觉、图像理解和软件测试技术。在研项目“强激光光学元件表面疵病检测技术研究”、“基于机器视觉的精密表面疵病检测系统”等，获得软件著作权3项，获发明专利3项。主要承担《软件项目管理》、《软件需求》等 课程教学工作。', '1000', '教授', '论牟泽波的重要性', null, '神级选手', '做审计选手，你也行');
INSERT INTO `teacher` VALUES ('2', null, '‫images/liyufeng.png', '李郁峰', '李郁峰', '副教授，博士，硕士生导师，计算机科学与技术学院实验教学中心主任。主要研究方向为多传感器图像融合、自动目 标识别技术。作为主要研发人员承担了包括国家自然科学基金和四川省教育厅基金等在内的5项课题；在国内外核心期刊上发表论文10余篇，EI检索2篇。国家发明专利2项，软件著作权2项。主要 承担《C++程序设计语言》等课程教学工作。', null, null, null, null, null, null);
INSERT INTO `teacher` VALUES ('3', null, 'images/panya.png', '潘娅', '潘娅', '副教授，硕士生导师，计算机科学与技术学院软件工程系系主任，中国计算机学会容错计算专委会委员，四川省精品课程《软件测试技术》主讲教师。主要研究方向为机器人视觉感知、软件测试技术。承担多项部省科研课题，申请国家发明专利1项。', null, null, null, null, null, null);
INSERT INTO `teacher` VALUES ('4', null, 'images/chenniannian.png', '陈念年', '陈念年', '副教授，博士，硕士生导师，计算机科学与技术学院实验教学中心主任。主要研究方向为多传感器图像融合、自动目 标识别技术。作为主要研发人员承担了包括国家自然科学基金和四川省教育厅基金等在内的5项课题；在国内外核心期刊上发表论文10余篇，EI检索2篇。国家发明专利2项，软件著作权2项。主要 承担《C++程序设计语言》等课程教学工作。', null, null, null, null, null, null);
