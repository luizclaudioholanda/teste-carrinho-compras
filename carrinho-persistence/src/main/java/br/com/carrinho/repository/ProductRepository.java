package br.com.carrinho.repository;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.carrinho.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository <Product, Long>{

	public Set<Product> findAllByOrderByNameAsc();
}
