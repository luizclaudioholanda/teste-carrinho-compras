package br.com.carrinho.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import br.com.carrinho.service.InterfaceCRUDService;

@Service
public class GenericCRUDServiceImpl<T, ID extends Serializable> implements InterfaceCRUDService<T, ID>{

	private CrudRepository<T, ID> repository;
	
	public void setRepository(CrudRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
		
	}

	@Override
	public T update(T entity) {
		return repository.save(entity);
	}

	@Override
	public Set<T> findAll() {
		return new HashSet<T>((Collection<? extends T>) repository.findAll());
	}

	@Override
	public T findOne(ID entityId) {
		return repository.findOne(entityId);
	}

}
