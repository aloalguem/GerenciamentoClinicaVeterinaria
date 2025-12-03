-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: odemarfim
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `agendamento`
--

DROP TABLE IF EXISTS `agendamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendamento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `horario` time(6) NOT NULL,
  `servico` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `pet_id` bigint NOT NULL,
  `procedimento_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKphmq75y49r3mjids3oqwbhltf` (`pet_id`),
  KEY `FK4q4jwthtddu1814aq4icxxf6k` (`procedimento_id`),
  CONSTRAINT `FK4q4jwthtddu1814aq4icxxf6k` FOREIGN KEY (`procedimento_id`) REFERENCES `procedimento` (`id`),
  CONSTRAINT `FKphmq75y49r3mjids3oqwbhltf` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendamento`
--

LOCK TABLES `agendamento` WRITE;
/*!40000 ALTER TABLE `agendamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `agendamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-03  7:38:31
