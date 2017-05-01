package br.com.carrinho.controller;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.carrinho.entity.Product;
import br.com.carrinho.service.impl.ProductServiceImpl;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure=false)
@WebMvcTest(ProductController.class)
public class ProductControllerTest extends AbstractControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductServiceImpl service;
	
	private final String URL_PATH = "/product";
	
	private final String OBJECT_TEST_NAME = "PRODUCT ONE"; 
	
	private final String OBJECT_TEST_DESCRIPTION = "DESCRIPTION ONE";
	
	private final String OBJECT_TEST_UPDATE_NAME = "UPDATE PRODUCT ONE";
	
	@Test
	public void createEntityTest() throws Exception {
		
		
		
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(URL_PATH+"/saveProduct")
							.content(asJsonString(createEntity(OBJECT_TEST_NAME)))
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isOk())
							.andReturn();
		
		String resp = result.getResponse().getContentAsString();
		
	}
	
	@Test
	public void findEntityTest() throws Exception{
		
		BDDMockito.given(this.service.findOne(21L))
			.willReturn(createEntityWithId(21L,OBJECT_TEST_DESCRIPTION, OBJECT_TEST_NAME,100D));
		
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(URL_PATH+"/getProduct/{entityId}",21L)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
	}
	
	@Test
	public void entityNotFoundTest() throws Exception{
		
		BDDMockito.given(this.service.findOne(21L))
			.willReturn(createEntityWithId(21L,OBJECT_TEST_DESCRIPTION, OBJECT_TEST_NAME,300D));
		
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(URL_PATH+"/getProduct/{entityId}",23L)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		String resp = result.getResponse().getContentAsString();
		
		Assertions.assertThat(resp).isEmpty();
	}
	
	@Test
	public void findAllEntitiesTest() throws Exception {
		
		BDDMockito.given(this.service.findAll())
		.willReturn(createSetEntities());
		
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(URL_PATH+"/getAllByOrderByNameAsc/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	

	@Test
	public void updateEntityTest() throws Exception {
		
		BDDMockito.given(this.service.update(createEntityWithId(10L,OBJECT_TEST_DESCRIPTION,OBJECT_TEST_UPDATE_NAME,100D)))
		.willReturn(createEntityWithId(10L,OBJECT_TEST_DESCRIPTION, OBJECT_TEST_UPDATE_NAME,100D));
		
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(URL_PATH+"/updateProduct/")
				.contentType(MediaType.APPLICATION_JSON).content(createJson(10L))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
	}
	
	private String createJson(Long id) throws JsonProcessingException{

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		return ow.writeValueAsString(createEntityWithId(id,OBJECT_TEST_DESCRIPTION,OBJECT_TEST_UPDATE_NAME,100D));
	}
	
	private Product createEntityWithId(Long id, String description, String name, Double price){
		
		Product entity = new Product();
		entity.setIdProduct(id);
		entity.setName(name);
		entity.setPrice(price);
		entity.setDescription(description);
		
		return entity;
	}
	
	private Product createEntity(String name){
		
		Product entity = new Product();
		
		entity.setDescription("RN:"+name);
		
		return entity;
	}
	
	private Set<Product> createSetEntities(){
		
		Set<Product> setEntities = new HashSet<Product>();
		
		setEntities.add(createEntityWithId(10L, "Product 1", "Name Prodcut 1", 100D));
		setEntities.add(createEntityWithId(20L, "Product 2", "Name Product 2", 200D));
		
		return setEntities;
	}

	
}
