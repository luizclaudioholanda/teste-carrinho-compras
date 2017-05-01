# teste-carrinho-compras
Simples carrinho de compras (ReactJS + Spring boot)

#Banco de dado MySQL
CREATE TABLE `PRODUCT` (
  `idProduct` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `description` varchar(400) DEFAULT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`idProduct`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

#End-Points
POST
/product/saveProduct
/product/updateProduct
/product/getAllByOrderByNameAsc

GET
/product/deleteProduct/{id}
/product/getProduct/{id}
