-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: shopmedb
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logo` varchar(128) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Canon','Canon.png'),(2,'Fujifilm','Fujifilm.png'),(3,'Sony','Sony.png'),(4,'HP','HP.png'),(5,'SanDisk','SanDisk.png'),(6,'Western Digital','Western Digital.png'),(7,'Panasonic','Panasonic.png'),(8,'Pelican','Pelican.png'),(9,'Apple','Apple.png'),(10,'Samsung','Samsung.png'),(11,'Olympus','Olympus.png'),(12,'CADeN','Caden.png'),(13,'AmazonBasics','amazonbasics.png'),(14,'Nikon','Nikon.png'),(15,'Neewer','Neewer.png'),(16,'Sigma','Sigma.png'),(17,'Bosch','Bosch.png'),(18,'Joby','Joby.png'),(19,'GoPro','GoPro.png'),(20,'Manfrotto','Manfrotto.png'),(21,'Google','Google.png'),(22,'LG','LG.png'),(23,'Motorola','Motorola.png'),(24,'Pantech','Pantech.png'),(25,'Huawei','Huawei.png'),(26,'Xiaomi','Xiaomi.png'),(27,'HOVAMP','Hovamp.png'),(28,'Aioneus','Aioneus.png'),(29,'XIAE','XIAE.png'),(30,'Everyworth','Everyworth.png'),(31,'Lexar','Lexar.png'),(32,'Nulaxy','Nulaxy.png'),(33,'Fitfort','Fitfort.png'),(34,'PopSockets','PopSocket.png'),(35,'SHAWE','SHAWE.png'),(36,'Lenovo','Lenovo.png'),(37,'Acer','Acer.png'),(38,'Dell','Dell.png'),(39,'Intel','Intel.png'),(40,'ASUS','ASUS.png'),(41,'Microsoft','Microsoft.png'),(42,'DragonTouch','DragonTouch.png'),(43,'AMD','AMD.png'),(44,'XFX','XFX.png'),(45,'MSI','MSI.png'),(46,'Seagate','Seagate.png'),(47,'Cosair','Corsair.png'),(48,'Thermaltake','Thermaltake.png'),(49,'Kingston','Kingston.png'),(50,'Creative','Creative.png'),(51,'Crucial','Crucial.png'),(52,'HyperX','HyperX.png'),(53,'Gigabyte','Gigabyte.png'),(54,'TP-Link','TP-Link.png');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands_categories`
--

DROP TABLE IF EXISTS `brands_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands_categories` (
  `brand_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`brand_id`,`category_id`),
  KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`),
  CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands_categories`
--

LOCK TABLES `brands_categories` WRITE;
/*!40000 ALTER TABLE `brands_categories` DISABLE KEYS */;
INSERT INTO `brands_categories` VALUES (6,1),(1,2),(2,2),(3,2),(7,2),(8,2),(9,4),(4,5),(9,5),(10,5),(36,5),(37,5),(38,5),(39,5),(40,5),(4,6),(9,6),(10,6),(36,6),(37,6),(38,6),(40,6),(9,7),(10,7),(36,7),(37,7),(38,7),(40,7),(41,7),(42,7),(8,9),(12,9),(13,9),(1,10),(3,10),(7,10),(11,10),(14,10),(1,11),(3,11),(14,11),(15,11),(1,12),(14,12),(16,12),(17,13),(18,13),(19,13),(20,13),(9,14),(10,14),(21,14),(22,14),(23,14),(24,14),(9,15),(10,15),(21,15),(23,15),(25,15),(26,15),(27,17),(28,17),(29,17),(30,17),(5,18),(10,18),(31,18),(20,19),(32,19),(33,19),(34,19),(35,19),(39,22),(43,22),(43,23),(44,23),(45,23),(5,24),(6,24),(10,24),(46,24),(10,25),(22,25),(40,25),(40,26),(47,26),(48,26),(5,27),(6,27),(10,27),(49,27),(40,28),(50,28),(5,29),(6,29),(10,29),(47,29),(49,29),(51,29),(52,29),(40,30),(45,30),(53,30),(5,31),(54,31);
/*!40000 ALTER TABLE `brands_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(64) NOT NULL,
  `enable` bit(1) NOT NULL,
  `image` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `parent` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `alias_UNIQUE` (`alias`),
  KEY `FKqxcgflvs2beh702toak6bni11` (`parent`),
  CONSTRAINT `FKqxcgflvs2beh702toak6bni11` FOREIGN KEY (`parent`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'electronics',_binary '','electronics.png','Electronics',NULL),(2,'camera',_binary '','camera.jpg','Camera & Photo',1),(3,'computers',_binary '','computers.png','Computers',NULL),(4,'cellphones',_binary '','cellphones.png','Cell Phones & Accessories',1),(5,'desktop_computers',_binary '','desktop computers.png','Desktops',3),(6,'laptop_computers',_binary '','laptop computers.png','Laptops',3),(7,'tablet_computers',_binary '','tablets.png','Tablets',3),(8,'computer_components',_binary '','computer components.png','Computer Components',3),(9,'camera_bags_cases',_binary '','bags_cases.png','Bags & Cases',2),(10,'digital_cameras',_binary '','digital cameras.png','Digital Cameras',2),(11,'camera_flashes',_binary '','flashes.png','Flashes',2),(12,'camera_lenses',_binary '','lenses.png','Lenses',2),(13,'camera_tripods_monopods',_binary '','tripods_monopods.png','Tripods & Monopods',2),(14,'carrier_cellphones',_binary '','carrier cellphones.png','Carrier Cell Phones',4),(15,'unlocked_cellphones',_binary '','unlocked cellphones.png','Unlocked Cell Phones',4),(16,'cellphone_accessories',_binary '','cellphone accessories.png','Accessories',4),(17,'cellphone_cables_adapters',_binary '','cables and adapters.png','Cables & Adapters',16),(18,'microsd_cards',_binary '','microsd cards.png','MicroSD Cards',16),(19,'cellphone_stands',_binary '','cellphone_stands.png','Stands',16),(20,'cellphone_cases',_binary '','cellphone cases.png','Cases',16),(21,'headphones',_binary '','headphones.png','Headphones',16),(22,'computer_processors',_binary '\0','computer processors.png','CPU Processors Unit',8),(23,'computer_graphic_cards',_binary '','graphic cards.png','Graphic Cards',8),(24,'hard_drive',_binary '\0','internal hard drive.png','Internal Hard Drives',8),(25,'computer_optical_drives',_binary '','internal optical drives.png','Internal Optical Drives',8),(26,'computer_power_supplies',_binary '','power supplies.png','Power Supplies',8),(27,'solid_state_drives',_binary '','solid state drives.png','Solid State Drives',8),(28,'computer_sound_cards',_binary '','sound cards.png','Sound Cards',8),(29,'computer_memory',_binary '','computer memory.png','Memory',8),(30,'computer_motherboard',_binary '','motherboards.png','Motherboard',8),(31,'computer_network_cards',_binary '','network cards.png','Network Cards',8);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Manage everything','Admin'),(2,'Manage product price, customers, shipping, orders and sales report','Salesperson'),(3,'Manage categories, brands, products, art','Editor'),(4,'View products, view orders and update order status','Shipper'),(5,'Manage questions and reviews','Assistant');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `enable` bit(1) DEFAULT NULL,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `photo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'cuong97ndc@gmail.com',_binary '','Cuong','Nguyen Tan','$2a$10$95xOCFgcUV7E9zLfBhowceXjrLOGimYfAYQVWpM../3sO4CerobN.','2C0468B9-3A00-450B-A010-AA4717CEACA1.jpeg'),(2,'kanna.allada@gmail.com',_binary '','Allada','Pavan','$2a$10$zRa/rmQ8JarpYG2bNKftyelKnsUhsHwGB.xmCKTWJClsB7O9wzTnG','Allada Pavan.png'),(3,'aecllc.bnk@gmail.com',_binary '','Bruce','Kitchell','$2a$10$GINThwCjVZAbGnmOe9BIeuDuvDlyfuwZrg/Rsmrjs1Lsq2pnXtO/S','Bruce Kitchell.png'),(4,'muhammad.evran13@gmail.com',_binary '\0','Muhammad','Evran','$2a$10$UcHWHC72azPVZJb5Ky.Yy.X695WGf1ZkkGMS3WL3B9WqWf2dQD04G','Muhammad Evran.png'),(5,'kent.hervey8@gmail.com',_binary '','Kent','Hervey','$2a$10$YHXRsZ07/Btv.qCgGht.7u2PW.GLWzpVB7eabfgH1mhTVVXffDT6K','KentHervey.png'),(6,'alfredephraim26@gmail.com',_binary '','Alfred','Ephraim','$2a$10$1jl3q3r/Fh9ZBv6ziM4XhuxCi2GMFWcfHUrxsesXAEwnsiV/NJKbq','Alfred.png'),(7,'nathihsa@gmail.com',_binary '','Nathi','Sangweni','$2a$10$WyHmQiXCSYuHcGeg8eFWvOScqzSgg88MmqpajPdzSkLsvZjT3tKC.','Nathi_Sangweni.png'),(8,'ammanollashirisha2020@gmail.com',_binary '','Ammanolla','Shirisha','$2a$10$N1eE87eXFB2XQ5nmWKaTXOofnrPn8koeNvZhEpleJzO49i55e/Vk.','Ammanolla.png'),(9,'bfeeny238@hotmail.com',_binary '','Bill','Feeny','$2a$10$3sH0v..zpjwA8ux5/q.OAeu0HgmSdMj8VzMWzhwwBDkE8wOISsUyi','Bill Feeny.png'),(10,'redsantosph@gmail.com',_binary '','Frederick','delos Santos','$2a$10$KXHmKpE6YB/0sjiy3fkMv.muKyxqvOXE6jVeaPu8KEaExx62ZmmNe','Frederick Santos.png'),(11,'ro_anamaria@mail.ru',_binary '','Ana','Maria','$2a$10$sz0CHOHAY1Xjt2ajIZgnG.L2KBQ4SsQkOGsPYue.C5gr6j.KMDdqS','Anna Maria.jpg'),(12,'maytassatya@hotmail.com',_binary '\0','Satya','Narayana','$2a$10$R7EJcaYijjJo/IVk6c1CieBML2uP3RAKMVlCxylPAePlCfJsX7OOy','Satya Narayana.png'),(13,'matthewefoli@gmail.com',_binary '','Matthew','Efoli','$2a$10$ECNnxXSVArnwS9KCet3yguQ1qHKyBIhh2G8c4F9CYgvp/Hadl8OS6','Matthew.png'),(14,'davekumara2@gmail.com',_binary '','Dave','Kumar','$2a$10$5ebeZu18V5RheieYqpl/LORCN41E3H7yvxKqEwtq5Zq2JVw.E9dva','Dave Kumar.png'),(15,'jackkbruce@yahoo.com',_binary '','Jack','Bruce','$2a$10$a6iyIHRj8DNpu15obVHTSOGcLe4IfpBcD4iEEJesWaFpBQvRoF2da','Jack Bruce.png'),(16,'zirri.mohamed@gmail.com',_binary '','Mohamed','Zirri','$2a$10$TmvyH1AoyDqRmQ4uC8NAZOOV29CPEDGuxVsHLP1cJoHQGr78L4kjW','Mohamed Zirri.jpg'),(17,'mk.majumdarndc@gmail.com',_binary '\0','Mithun','Kumar Majumdar','$2a$10$Y6SEy2INN0Rk/vhLHHJUYO6IMqNW3Ar.jVe9o0W1lpBRX8xr2Itui','Mithun Kumar Majumdar.png'),(18,'aliraza.arain.28@gmail.com',_binary '','Ali','Raza','$2a$10$PISZ2KitSbhE4/Z3dtIGk.WUi2ILiDl4PzRUDEQSp5BJIxcdcPq4G','Ali Raza.png'),(19,'isaachenry2877@gmail.com',_binary '','Isaac','Henry','$2a$10$CtmhrOz/AhDoCpKbeYl8O.0ngCFMukcznNZq7.YcHrkRyKpBG8Zca','Isaac Henry.jpg'),(20,'s.stasovska82@hotmail.com',_binary '','Svetlana','Stasovska','$2a$10$fcN2cNa7vB.78QnmzfNZEeUBkrwUaM.bVK3iDB.KFQlR15DwL7QZy','Svetlana Stasovska.png'),(21,'mikegates2012@gmail.com',_binary '\0','Mike','Gates','$2a$10$zIO1tygsw6cB2ymiR.WX0ulr9NKdTlZHqu7w/W/LLwk8HhK7nVnH.','Mike Gates.png'),(22,'pedroquintero67@gmail.com',_binary '\0','Pedro','Quintero','$2a$10$UPX5EwZw0MyBvbe.7mxg2u8GOl/4KgaUU40iSjr1PLFYvhu35fMmu','Pedro Quintero.png'),(23,'amina.elshal2@yahoo.com',_binary '','Amina','Elshal','$2a$10$J1yoyqG5vWNe5N663PkgY.h53gfJtTR7Bb8E8u3sXdNbOZxhXgHu.','Amina Elshal.png');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,1),(3,2),(9,2),(10,2),(11,2),(12,2),(13,2),(19,2),(20,2),(4,3),(5,3),(6,3),(7,3),(8,3),(11,3),(14,3),(15,3),(18,3),(20,3),(14,4),(15,4),(16,4),(17,4),(18,4),(5,5),(14,5),(19,5),(20,5),(21,5),(22,5),(23,5);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-25  0:16:52
