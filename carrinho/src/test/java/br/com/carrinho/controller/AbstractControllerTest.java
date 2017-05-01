package br.com.carrinho.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractControllerTest {

	protected String asJsonString(Object obj){
		
		try {
	        
			final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        
	        return jsonContent;
	        
	    } catch (Exception e) {
	    	
	        throw new RuntimeException(e);
	    } 
	}
}
