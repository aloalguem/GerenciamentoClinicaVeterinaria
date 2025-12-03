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
-- Table structure for table `estoque`
--

DROP TABLE IF EXISTS `estoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estoque` (
  `preco_venda` float NOT NULL,
  `qntd` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  `imagem` varchar(255) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoque`
--

LOCK TABLES `estoque` WRITE;
/*!40000 ALTER TABLE `estoque` DISABLE KEYS */;
INSERT INTO `estoque` VALUES (70,98,1,'Antibiótico de amplo espectro, usado em infecções respiratórias, urinárias e de pele.','/assets/img/estoque/1.png','Amoxicilina + Clavulanato','Antibióticos'),(39.99,99,2,'Antibiótico eficaz em infecções bacterianas graves em cães e gatos.','/assets/img/estoque/2.png','Enrofloxacina','Antibióticos'),(40.5,99,3,'Usado no tratamento de erliquiose, leptospirose e outras doenças transmitidas por carrapatos.','/assets/img/estoque/3.png','Doxiciclina','Antibióticos'),(65,99,4,'Antibiótico comum para infecções de pele, ouvidos e urinárias.','/assets/img/estoque/4.png','Cefalexina','Antibióticos'),(16.3,99,5,'Antibiótico para infecções respiratórias e urinárias.','/assets/img/estoque/5.png','Sulfametoxazol + Trimetoprima','Antibióticos'),(60,99,6,'Anti-inflamatório não esteroidal indicado para dores articulares e pós-operatório.','/assets/img/estoque/6.png','Carprofeno','Anti-inflamatórios e analgésicos'),(24.99,99,7,'Reduz inflamações e dores crônicas em cães e gatos.','/assets/img/estoque/7.png','Meloxicam','Anti-inflamatórios e analgésicos'),(139.99,99,8,'Analgésico e anti-inflamatório para osteoartrite e dor musculoesquelética.','/assets/img/estoque/8.png','Firocoxibe (Previcox)','Anti-inflamatórios e analgésicos'),(29.5,99,9,'Analgésico e antitérmico de uso veterinário.','/assets/img/estoque/9.png','Dipirona sódica','Anti-inflamatórios e analgésicos'),(99,99,10,'Analgésico indicado para dores moderadas a intensas (uso restrito).','/assets/img/estoque/10.png','Tramadol','Anti-inflamatórios e analgésicos'),(45.99,99,11,'Antisséptico, ajuda no controle de infecções bacterianas na pele.','/assets/img/estoque/11.png','Shampoo de Clorexidina','Dermatológicos e pele'),(49.99,99,12,'Auxilia no tratamento de micoses e dermatites.','/assets/img/estoque/12.png','Shampoo Cetoconazol + Clorexidina','Dermatológicos e pele'),(20.5,99,13,'Tratamento de infecções fúngicas e bacterianas localizadas.','/assets/img/estoque/13.png','Pomadas com Miconazol ou Neomicina','Dermatológicos e pele'),(50,99,14,'Aceleram a cicatrização de feridas e previnem infecções.','/assets/img/estoque/14.png','Sprays cicatrizantes','Dermatológicos e pele'),(119.99,99,15,'Suplemento para pele, pelos, articulações e saúde cardiovascular.','/assets/img/estoque/15.png','Ômega 3 para pets','Vitaminas e suplementos'),(94.99,99,16,'Suporte para articulações, indicado para cães idosos ou com displasia.','/assets/img/estoque/16.png','Condroprotetores (Condroplex, Cosequin)','Vitaminas e suplementos'),(229.49,99,17,'Polivitamínicos para suporte nutricional diário.','/assets/img/estoque/17.png','Pet Tabs / Gerioox','Vitaminas e suplementos'),(59,99,18,'Auxilia na eliminação de bolas de pelo e melhora a digestão.','/assets/img/estoque/18.png','Pasta de malte (para gatos)','Vitaminas e suplementos'),(45,99,19,'Pipeta tópica contra pulgas e carrapatos, ação rápida.','/assets/img/estoque/19.png','Frontline (fipronil)','Antipulgas e vermífugos de venda livre'),(129.99,99,20,'Antipulgas em pipeta, eficaz por até 4 semanas.','/assets/img/estoque/20.png','Advantage (imidacloprida)','Antipulgas e vermífugos de venda livre'),(80,99,21,'Proteção prolongada contra pulgas, carrapatos e mosquitos.','/assets/img/estoque/21.png','Coleira Seresto / Scalibor','Antipulgas e vermífugos de venda livre'),(34.99,99,22,'Vermífugo em comprimido, atua contra vermes intestinais.','/assets/img/estoque/22.png','Vermivet','Antipulgas e vermífugos de venda livre'),(42,99,23,'Vermífugo em comprimido, atua contra vermes intestinais.','/assets/img/estoque/23.png','Endogard','Antipulgas e vermífugos de venda livre');
/*!40000 ALTER TABLE `estoque` ENABLE KEYS */;
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
