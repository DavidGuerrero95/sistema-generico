CREATE DATABASE  IF NOT EXISTS `proyecto` 
USE `proyecto`;

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `precio` bigint NOT NULL,
  `stock` int NOT NULL,
  `descripcion` varchar(45),
  `fechaIngreso;` datetime DEFAULT NULL,
  `id_categorias` bigint NOT NULL,
  `id_provedor` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_categorias_idx` (`id_categorias`),
  KEY `id_provedor_idx` (`id_provedor`),
  CONSTRAINT `id_categorias` FOREIGN KEY (`id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `id_provedor` FOREIGN KEY (`id`) REFERENCES `provedor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


