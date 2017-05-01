package br.com.carrinho.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.carrinho.entity.Product;
import br.com.carrinho.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController<Product, Long>{

	@Autowired
	private ProductService service;
	
	public ProductController(ProductService service) {
		super(service);
		this.service = service;
	}
	
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Product saveProduct(@RequestBody Product entity) {

		return service.save(entity);
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Product updateProduct(@RequestBody Product entity) {

		return service.update(entity);
	}
	
	@RequestMapping(value = "/getAllByOrderByNameAsc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<Product> getAllByOrderByNameAsc() {

		return service.findAllByOrderByNameAsc();
	}
	
	@RequestMapping(value="/getAllProductByPaging", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Product> getAllProductsByPaging(Pageable pageable){
		
		Page<Product> entities = service.findAllByName(pageable);
		return entities;
	}
	
	@RequestMapping(value = "/deleteProduct/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteProduct(@PathVariable("id") Long id){
		
		Product entity = service.findOne(id);
		
		if(entity != null){
			
			service.delete(entity);
		}
	}
	
	@RequestMapping(value = "/getProduct/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Product getProduct(@PathVariable("id") Long id){
		
		return service.findOne(id);
				
	}
}
