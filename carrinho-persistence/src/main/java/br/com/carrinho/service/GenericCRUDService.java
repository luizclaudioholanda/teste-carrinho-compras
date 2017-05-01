package br.com.carrinho.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public abstract class GenericCRUDService<T, ID extends Serializable> {

	private CrudRepository<T, ID> repository;
	
	public GenericCRUDService(CrudRepository<T, ID> repository){
		this.repository = repository;
	}
	
	public T save(T entity) {
        return repository.save(entity);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }
    
    public T update(T entity) {
        return repository.save(entity);
    }

    public T find(ID entityId) {
    	
        return repository.findOne(entityId);
    }

    public Set<T> findAll() {
    	
        return new HashSet<T>((Collection<? extends T>) repository.findAll());
    }
}
