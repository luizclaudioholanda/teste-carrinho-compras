package br.com.carrinho.controller;

import java.io.Serializable;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.carrinho.service.InterfaceCRUDService;

public abstract class AbstractController <T, ID extends Serializable>{

	private InterfaceCRUDService<T, ID> service;
	
	public AbstractController(InterfaceCRUDService<T, ID> service){
		this.service = service;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody T save(@RequestBody T entity) {

		return service.save(entity);
	} 
	
	@RequestMapping(value = "/get/{entityId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody T get(@PathVariable("entityId") ID entityId) {

		return service.findOne(entityId);
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<T> getAll() {

		return service.findAll();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity delete(@RequestBody T entity) {

		service.delete(entity);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody T update(@RequestBody T entity) {

		return service.save(entity);		
	}
}
