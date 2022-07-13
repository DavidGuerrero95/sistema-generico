CREATE DATABASE  IF NOT EXISTS `proyecto` 
USE `proyecto`;

DROP TABLE IF EXISTS `categorias`;

CREATE TABLE `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `fechaInicial` varchar(45)
  `fechaFinal` varchar(45),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


