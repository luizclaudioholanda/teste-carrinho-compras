# teste-carrinho-compras
Simples carrinho de compras (ReactJS + Spring boot)

#Banco de dado MySQL</br>
CREATE TABLE `PRODUCT` (</br>
  `idProduct` bigint(20) NOT NULL AUTO_INCREMENT,</br>
  `name` varchar(60) NOT NULL,</br>
  `description` varchar(400) DEFAULT NULL,</br>
  `price` double NOT NULL,</br>
  PRIMARY KEY (`idProduct`)</br>
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;</br>

#End-Points</br>
POST</br>
/product/saveProduct</br>
/product/updateProduct</br>
/product/getAllByOrderByNameAsc</br>
</br></br>
GET</br>
/product/deleteProduct/{id}</br>
/product/getProduct/{id}</br>
