/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.11 : Database - vblog
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

insert  into `demo_user`(`user_id`,`user_name`,`user_regist_time`,`user_description`) values (1,'liyang','2019-05-30 11:12:59','drtstrtr'),(7,'liyang','2019-05-30 16:18:59','你好'),(8,'liyang','2019-05-30 16:57:34','你好'),(9,'liyang','2019-06-01 12:11:30','你好'),(10,'liyang222','2019-06-01 12:11:35','你好'),(11,'liyang333','2019-06-01 12:11:39','你好'),(12,'liyang444','2019-06-01 12:11:42','你好'),(13,'liyang555','2019-06-01 12:11:45','你好'),(14,'liyang666','2019-06-01 12:11:47','你好'),(15,'liyang777','2019-06-01 12:11:49','你好'),(16,'liyang888','2019-06-01 12:11:53','你好'),(17,'liyang999','2019-06-01 12:11:56','你好'),(18,'123','2019-06-02 00:03:15','31120220');

/*Table structure for table `demo_user_pwd` */

DROP TABLE IF EXISTS `demo_user_pwd`;

CREATE TABLE `demo_user_pwd` (
  `demo_user_pwd_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_pwd` varchar(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`demo_user_pwd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `demo_user_pwd` */

insert  into `demo_user_pwd`(`demo_user_pwd_id`,`user_pwd`,`user_id`) values (1,'123456',1),(2,'123456',7),(3,'123456',8),(4,'123456',9),(5,'123456',10),(6,'123456',11),(7,'123456',12),(8,'123456',13),(9,'123456',14),(10,'123456',15),(11,'123456',16),(12,'123456',17),(13,'123123',18);

/*Table structure for table `vblog_article` */

DROP TABLE IF EXISTS `vblog_article`;

CREATE TABLE `vblog_article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `virtual_id` varchar(32) NOT NULL,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `author_id` int(11) NOT NULL,
  `type_1` int(11) NOT NULL,
  `type_2` int(11) DEFAULT NULL,
  `cover` int(11) DEFAULT NULL,
  `hidden` int(11) NOT NULL DEFAULT '0',
  `content` text NOT NULL,
  `abstract` text NOT NULL,
  `release_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`article_id`),
  UNIQUE KEY `virtual_id` (`virtual_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_article` */

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_article_dynamic` */

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
  `article_id` varchar(32) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comment_time` datetime NOT NULL,
  `parent_comment_id` int(11) NOT NULL,
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
  `lable_id` int(11) NOT NULL AUTO_INCREMENT,
  `lable_name` varchar(20) NOT NULL,
  `description` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`lable_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_label` */

/*Table structure for table `vblog_report_record` */

DROP TABLE IF EXISTS `vblog_report_record`;

CREATE TABLE `vblog_report_record` (
  `article_id` int(11) NOT NULL,
  `reporter_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `report_time` datetime NOT NULL,
  `handle_time` datetime DEFAULT NULL,
  `handle_result` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_report_record` */

/*Table structure for table `vblog_resource` */

DROP TABLE IF EXISTS `vblog_resource`;

CREATE TABLE `vblog_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `path` text NOT NULL,
  `encrypt_file` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `upload_time` datetime NOT NULL,
  `url` text NOT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_resource` */

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
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `expiry_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_token` */

/*Table structure for table `vblog_user` */

DROP TABLE IF EXISTS `vblog_user`;

CREATE TABLE `vblog_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `img` int(11) DEFAULT NULL,
  `signature` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `interest_1` int(11) NOT NULL,
  `interest_2` int(11) DEFAULT NULL,
  `interest_3` int(11) DEFAULT NULL,
  `identity` int(11) NOT NULL DEFAULT '1',
  `ban` int(11) NOT NULL DEFAULT '0',
  `register_time` datetime NOT NULL,
  `salt` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `account` (`account`,`email`,`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vblog_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
