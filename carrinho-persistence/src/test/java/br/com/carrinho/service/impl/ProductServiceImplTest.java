package br.com.carrinho.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.carrinho.entity.Product;
import br.com.carrinho.repository.ProductRepository;
import br.com.carrinho.service.AbstractGenericTestService;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest extends AbstractGenericTestService<Product, Long>{

	@Mock
	private Product Product;
	
	@InjectMocks
	private ProductServiceImpl service;
	
	@Mock
	private ProductRepository dao;
	
	private Long identifier = 10L;
	
	@Before
	public void initDaoServiceAndIdentifierToSuper() {
		super.setDao(dao);
		super.setService(service);
		super.setIdentifier(identifier);
		
	}
	
	@Test
	public void ProductServiceTest(){
		ProductServiceImpl entity = new ProductServiceImpl();
	}
	
	@Override
	public Product createObjectToTest(Long identifier) {
		
		Product entity = new Product();
		entity.setIdProduct(identifier);
		entity.setDescription("DESCRIPTION: "+identifier);
		entity.setPrice(100D);
		
		return entity;
	}

	@Override
	public Set<Product> createList() {
		
		Set<Product> setProducts = new HashSet<Product>();
		Product p1 = createObjectToTest(20L);
		Product p2 = createObjectToTest(50L);
		
		setProducts.add(p1);
		setProducts.add(p2);
		
		return setProducts;
	}
	
}
