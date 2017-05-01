package br.com.carrinho.service;

import java.io.Serializable;
import java.util.Set;

public interface InterfaceCRUDService<T, ID extends Serializable> {
	
	T save(T entity);
	
	void delete(T entity);

	T update(T entity);
	
	Set<T> findAll();
	
	T findOne(ID entityId);
}
