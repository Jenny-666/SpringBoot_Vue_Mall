-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: management
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `buyer`
--

DROP TABLE IF EXISTS `buyer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buyer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `buyername` varchar(16) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `nick_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int DEFAULT NULL,
  `sex` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `role` int DEFAULT '3',
  `status` int DEFAULT '0',
  `wallet` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer`
--

LOCK TABLES `buyer` WRITE;
/*!40000 ALTER TABLE `buyer` DISABLE KEYS */;
INSERT INTO `buyer` VALUES (1,'zhen','zhen','嘻嘻',21,'女','山东工商学院西校区20号楼','180',3,1,32),(7,'1808080','000',NULL,NULL,NULL,NULL,NULL,3,0,0);
/*!40000 ALTER TABLE `buyer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `goodsName` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `userId` int NOT NULL,
  `userName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `kindName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` double NOT NULL,
  `instruction` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `num` int NOT NULL,
  `status` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `goods_user_id_fk_idx` (`userId`),
  CONSTRAINT `goods_user_id_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (3,'太阳镜','http://localhost:9090/files/b140aa96eee340aba832939fae732a60',5,'222','首饰',19,'好看完好，适合出去游玩戴',50,1),(4,'笔记本','http://localhost:9090/files/2e2a536b1b1245b795fc85a8f8962181',2,'111','文具',2,'全新完好，价格实惠！！！',86,1),(6,'人工智能二手书','http://localhost:9090/files/17d5099f58be4ccdbd8787f28277635d',2,'111','图书',10,'九成新',4,1),(34,'耳机','http://localhost:9090/files/826e8a58ad414fa4b3ef11e3aa8cfb61',2,'111','数码',29.9,'有线耳机，九成新，声效非常好！',6,1),(35,'OPPO Reno4','http://localhost:9090/files/2e6d0975a2a749fdb35c358d00d49f2a',2,'111','手机',999,'八成新，屏幕完好',3,1),(43,'鼠标','http://localhost:9090/files/48107cd61ea74d11beb9108ef9c11a64',5,'222','数码',15,'蓝牙鼠标，无延迟，不卡顿',20,1),(44,'键盘','http://localhost:9090/files/17cb9d815aac4692a108890a741f68b3',5,'222','数码',78,'游戏键盘，青轴，九成新',10,1),(45,'学习桌','http://localhost:9090/files/3db0ffe6f73d416f8ab860062588325e',6,'333','家具',49,'完好，适合孩子学习',2,1),(51,'小熊抱枕','http://localhost:9090/files/e47d638f4b134525bf91390e1aabf5e8',6,'333','玩具',19,'全新！好看可爱女孩子!',10,0);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kind`
--

DROP TABLE IF EXISTS `kind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kind` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kind`
--

LOCK TABLES `kind` WRITE;
/*!40000 ALTER TABLE `kind` DISABLE KEYS */;
INSERT INTO `kind` VALUES (9,'图书'),(8,'家具'),(3,'手机'),(1,'数码'),(13,'文具'),(10,'服饰'),(12,'玩具'),(11,'电器'),(4,'美妆'),(2,'首饰');
/*!40000 ALTER TABLE `kind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'有闲新闻！！！','<h1 id=\"tj3qu\">?<font face=\"楷体\">有闲商城最新发布</font><br/></h1><p style=\"padding-left:14em;\"><font face=\"仿宋\" size=\"6\">请新老用户文明购买！</font><br/></p>','2023-05-13 09:54:21'),(3,'618大促','<h1 id=\"x0vol\">新品上架！全场八折！</h1>','2023-05-15 11:43:01');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `goodsId` int DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `buyerId` int DEFAULT NULL,
  `goodsName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `num` int DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  `creatTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0',
  `endTime` datetime DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `Order_goodsId_fk_idx` (`goodsId`),
  KEY `Order_userId_fk_idx` (`userId`),
  KEY `Order_buyerId_fk_idx` (`buyerId`),
  CONSTRAINT `Order_buyerId_fk` FOREIGN KEY (`buyerId`) REFERENCES `buyer` (`id`),
  CONSTRAINT `Order_goodsId_fk` FOREIGN KEY (`goodsId`) REFERENCES `goods` (`id`),
  CONSTRAINT `Order_userId_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (228,6,2,1,'人工智能二手书',1,10,'2023-06-16 11:09:38',2,'2023-06-16 11:21:25'),(229,4,2,1,'笔记本',1,2,'2023-06-16 11:21:42',1,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swiper`
--

DROP TABLE IF EXISTS `swiper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swiper` (
  `id` int NOT NULL AUTO_INCREMENT,
  `photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swiper`
--

LOCK TABLES `swiper` WRITE;
/*!40000 ALTER TABLE `swiper` DISABLE KEYS */;
INSERT INTO `swiper` VALUES (1,'http://localhost:9090/files/2416098746c3499385e72fc458dd822b'),(2,'http://localhost:9090/files/1ec340d3bdae40eeb7a738d1eaef7390'),(3,'http://localhost:9090/files/7e79c767f45f4ac4b22bd3b39d318c13'),(4,'http://localhost:9090/files/f43a7fe5ac504f468817b118454cacdc'),(5,'http://localhost:9090/files/5a54f9b1b349444694a74fe1a4485c63');
/*!40000 ALTER TABLE `swiper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `nick_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int DEFAULT NULL,
  `sex` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `role` int DEFAULT '2',
  `status` int DEFAULT '0',
  `level` int DEFAULT '5',
  `wallet` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','admin',20,'女','啦啦啦啦啦','18686',1,1,5,0),(2,'111','111','zz',21,'男','吉林省长春市','1555',2,1,5,191),(5,'222','222','张三',20,'男','山东工商学院西校区','111',2,1,5,500),(6,'333','333','嘻嘻',20,'女','山东工商学院','17040401234',2,1,5,224),(11,'555','555',NULL,NULL,NULL,NULL,NULL,2,2,5,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-16 14:53:08
