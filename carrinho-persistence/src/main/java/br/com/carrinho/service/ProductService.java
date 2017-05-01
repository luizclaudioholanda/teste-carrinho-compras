package br.com.carrinho.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.carrinho.entity.Product;

public interface ProductService extends InterfaceCRUDService<Product, Long> {

	Set<Product> findAllByOrderByNameAsc();
	
	Page<Product> findAllByName(Pageable pageable);

}