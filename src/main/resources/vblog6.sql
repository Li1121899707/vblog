/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.22-log : Database - vblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `vblog`;

/*Table structure for table `demo_user` */

DROP TABLE IF EXISTS `demo_user`;

CREATE TABLE `demo_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_regist_time` datetime NOT NULL,
  `user_description` text NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `demo_user` */

insert  into `demo_user`(`user_id`,`user_name`,`user_regist_time`,`user_description`) values 
(1,'liyang','2019-05-30 11:12:59','drtstrtr'),
(2,'liyang','2019-05-30 16:18:59','你好'),
(8,'liyang','2019-05-30 16:57:34','你好'),
(9,'liyang','2019-06-01 12:11:30','你好'),
(10,'liyang222','2019-06-01 12:11:35','你好'),
(11,'liyang333','2019-06-01 12:11:39','你好'),
(12,'liyang444','2019-06-01 12:11:42','你好'),
(13,'liyang555','2019-06-01 12:11:45','你好'),
(14,'liyang666','2019-06-01 12:11:47','你好'),
(15,'liyang777','2019-06-01 12:11:49','你好'),
(16,'liyang888','2019-06-01 12:11:53','你好'),
(17,'liyang999','2019-06-01 12:11:56','你好'),
(18,'123','2019-06-02 00:03:15','31120220');

/*Table structure for table `demo_user_pwd` */

DROP TABLE IF EXISTS `demo_user_pwd`;

CREATE TABLE `demo_user_pwd` (
  `demo_user_pwd_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_pwd` varchar(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`demo_user_pwd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `demo_user_pwd` */

insert  into `demo_user_pwd`(`demo_user_pwd_id`,`user_pwd`,`user_id`) values 
(1,'123456',1),
(2,'123456',7),
(3,'123456',8),
(4,'123456',9),
(5,'123456',10),
(6,'123456',11),
(7,'123456',12),
(8,'123456',13),
(9,'123456',14),
(10,'123456',15),
(11,'123456',16),
(12,'123456',17),
(13,'123123',18);

/*Table structure for table `vblog_article` */

DROP TABLE IF EXISTS `vblog_article`;

CREATE TABLE `vblog_article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `virtual_id` varchar(32) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author_id` int(11) NOT NULL,
  `cover` text,
  `hidden` int(11) NOT NULL DEFAULT '0',
  `content` text NOT NULL,
  `article_abstract` text NOT NULL,
  `release_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`article_id`),
  UNIQUE KEY `virtual_id` (`virtual_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `vblog_article` */

insert  into `vblog_article`(`article_id`,`virtual_id`,`title`,`author_id`,`cover`,`hidden`,`content`,`article_abstract`,`release_time`,`update_time`) values 
(1,'ac59f05f2d111eca6fc6685333b43998','今天，昨天，前天',1,'http://123123.png',0,'今天我很开心，昨天我也很开心，前天我依然很开心，沙雕吗','123123','2019-06-04 13:52:29','2019-06-04 13:52:29'),
(2,'3674c9cd371671960510cda0370fe5c2','今天，昨天，前天2',2,'http://123123.png',0,'今天我很开心，昨天我也很开心，前天我依然很开心，沙雕吗2','111111111','2019-06-04 13:53:53','2019-06-04 13:53:53');

/*Table structure for table `vblog_article_dynamic` */

DROP TABLE IF EXISTS `vblog_article_dynamic`;

CREATE TABLE `vblog_article_dynamic` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `virtual_id` varchar(32) NOT NULL,
  `reading_num` int(11) NOT NULL DEFAULT '0',
  `thumb_num` int(11) NOT NULL DEFAULT '0',
  `collection_num` int(11) NOT NULL DEFAULT '0',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `vblog_article_dynamic` */

insert  into `vblog_article_dynamic`(`article_id`,`virtual_id`,`reading_num`,`thumb_num`,`collection_num`,`comment_num`) values 
(1,'ac59f05f2d111eca6fc6685333b43998',0,0,0,0),
(2,'3674c9cd371671960510cda0370fe5c2',0,0,0,0);

/*Table structure for table `vblog_article_label` */

DROP TABLE IF EXISTS `vblog_article_label`;

CREATE TABLE `vblog_article_label` (
  `article_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  `label_add_time` datetime NOT NULL,
  UNIQUE KEY `article_id` (`article_id`,`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_article_label` */

insert  into `vblog_article_label`(`article_id`,`label_id`,`label_add_time`) values 
(1,1,'2019-06-04 13:53:17'),
(1,2,'2019-06-04 13:53:20'),
(2,1,'2019-06-04 13:54:10');

/*Table structure for table `vblog_collection_record` */

DROP TABLE IF EXISTS `vblog_collection_record`;

CREATE TABLE `vblog_collection_record` (
  `artical_id` int(11) NOT NULL,
  `collector_id` int(11) NOT NULL,
  `collect_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_collection_record` */

/*Table structure for table `vblog_comment` */

DROP TABLE IF EXISTS `vblog_comment`;

CREATE TABLE `vblog_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment` varchar(300) NOT NULL,
  `comment_time` datetime NOT NULL,
  `parent_comment_id` int(11) NOT NULL,
  `comment_hide` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_comment` */

/*Table structure for table `vblog_concern_record` */

DROP TABLE IF EXISTS `vblog_concern_record`;

CREATE TABLE `vblog_concern_record` (
  `target_id` int(11) NOT NULL,
  `follower_id` int(11) NOT NULL,
  `concern_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_concern_record` */

/*Table structure for table `vblog_label` */

DROP TABLE IF EXISTS `vblog_label`;

CREATE TABLE `vblog_label` (
  `label_id` int(11) NOT NULL AUTO_INCREMENT,
  `label_name` varchar(20) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `vblog_label` */

insert  into `vblog_label`(`label_id`,`label_name`,`description`) values 
(1,'123','456454123'),
(2,'io','45477hfhghf'),
(3,'sdasad','dsdsdsfsdgsffdadsa'),
(4,'www','sdasda');

/*Table structure for table `vblog_report_record` */

DROP TABLE IF EXISTS `vblog_report_record`;

CREATE TABLE `vblog_report_record` (
  `article_id` int(11) NOT NULL,
  `reporter_id` int(11) NOT NULL,
  `reason` varchar(500) NOT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `report_time` datetime NOT NULL,
  `handle_time` datetime DEFAULT NULL,
  `handle_result` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_report_record` */

/*Table structure for table `vblog_thumb_record` */

DROP TABLE IF EXISTS `vblog_thumb_record`;

CREATE TABLE `vblog_thumb_record` (
  `article_id` int(11) NOT NULL,
  `thumber_id` int(11) NOT NULL,
  `thumb_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_thumb_record` */

/*Table structure for table `vblog_token` */

DROP TABLE IF EXISTS `vblog_token`;

CREATE TABLE `vblog_token` (
  `user_id` int(11) NOT NULL,
  `token` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `expiry_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_token` */

insert  into `vblog_token`(`user_id`,`token`,`create_time`,`expiry_time`) values 
(1,'3ccd68af8ae4bf1c31eb53656f4bbab5','2019-06-02 21:56:05','2019-06-29 21:56:23');

/*Table structure for table `vblog_user` */

DROP TABLE IF EXISTS `vblog_user`;

CREATE TABLE `vblog_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `signature` varchar(300) DEFAULT NULL,
  `allowance` int(11) NOT NULL DEFAULT '1',
  `ban` int(11) NOT NULL DEFAULT '0',
  `register_time` datetime NOT NULL,
  `salt` int(11) NOT NULL,
  `avatar_lg` varchar(300) DEFAULT NULL,
  `avatar_md` varchar(300) DEFAULT NULL,
  `avatar_sm` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `account` (`account`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `vblog_user` */

insert  into `vblog_user`(`user_id`,`account`,`pwd`,`nickname`,`email`,`phone`,`signature`,`allowance`,`ban`,`register_time`,`salt`,`avatar_lg`,`avatar_md`,`avatar_sm`) values 
(1,'z2wenME_','9bd8bcb9f33f130e1f2e249028475c91','z臧博浩','1121899707@qb.com','z17863105124',NULL,1,0,'2019-06-04 10:58:28',2272,'zhttp://s221d22.png','zhttp://s44d152s15d2.png','zhttp://41154125.png'),
(2,'z2wenME_222','2','2','11218299707@qb.com','z178631205124',NULL,1,0,'2019-06-04 10:58:28',2272,'zhttp://s221d22.png','zhttp://s44d152s15d2.png','zhttp://41154125.png');

/*Table structure for table `vblog_user_interest` */

DROP TABLE IF EXISTS `vblog_user_interest`;

CREATE TABLE `vblog_user_interest` (
  `user_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  UNIQUE KEY `user_id` (`user_id`,`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_user_interest` */

insert  into `vblog_user_interest`(`user_id`,`label_id`) values 
(7,1),
(7,2),
(7,3),
(10,1),
(10,2),
(10,3),
(11,1),
(11,2),
(11,3),
(12,1),
(12,2),
(12,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
