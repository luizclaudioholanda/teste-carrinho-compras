package br.com.carrinho.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.Set;

import org.junit.Before;
import org.springframework.data.repository.CrudRepository;

import junit.framework.Assert;

public abstract class AbstractGenericBaseTestService <T, ID extends Serializable>{

	private InterfaceCRUDService<T, ID> service;
	
	private CrudRepository<T, ID> dao;
	
	private ID identifier;
	
	 public void findByIdSuccess() {
	        T object = createObjectToTest(identifier);
	        when(dao.findOne(identifier)).thenReturn(object);

	        Assert.assertTrue(object.equals(service.findOne(identifier)));
	    }

	    public void saveSuccess() {
	        T object = createObjectToTest(identifier);
	        service.save(object);
	        verify(dao).save(object);
	    }

	    public void deleteSuccess() {
	        T object = createObjectToTest(identifier);
	        service.save(object);
	        verify(dao).save(object);

	        service.delete(object);
	        verify(dao).delete(object);
	    }

	    public void updateSuccess() {
	        T object = createObjectToTest(identifier);
	        service.update(object);
	        verify(dao).save(object);

	    }

	    public void allObjectsSuccessfully() {
	        Set<T> list = createList();
	        when(dao.findAll()).thenReturn(list);

	        Assert.assertEquals(list, service.findAll());
	    }

	    public abstract T createObjectToTest(ID identifier);

	    public abstract Set<T> createList();

	    @Before
	    public abstract void initDaoServiceAndIdentifierToSuper();

	    public void setService(InterfaceCRUDService<T, ID> service) {
	        this.service = service;
	    }

	    public void setDao(CrudRepository<T, ID> dao) {
	        this.dao = dao;
	    }

	    public void setIdentifier(ID identifier) {
	        this.identifier = identifier;
	    }
}
