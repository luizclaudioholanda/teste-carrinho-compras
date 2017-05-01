package br.com.carrinho.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.carrinho.entity.Product;
import br.com.carrinho.repository.ProductRepository;
import br.com.carrinho.service.ProductService;

@Service
public class ProductServiceImpl extends GenericCRUDServiceImpl<Product, Long> implements ProductService{

	@Autowired
	private ProductRepository repository;
	
	public ProductServiceImpl(){
		
	}
	
	@Autowired
	public ProductServiceImpl(ProductRepository repository) {

		super.setRepository(repository);
	}

	@Override
	public Set<Product> findAllByOrderByNameAsc() {
		
		return repository.findAllByOrderByNameAsc();
	}

	@Override
	public Page<Product> findAllByName(Pageable pageable) {

		return repository.findAll(pageable);
	}	
	
	

}
