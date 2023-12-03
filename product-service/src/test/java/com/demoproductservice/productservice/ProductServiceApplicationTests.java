package com.demoproductservice.productservice;

import com.demoproductservice.productservice.dto.ProductRequest;
import com.demoproductservice.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
//very important to get http status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void contextLoads() {
	}

	void mustCreateProductInstance() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productrequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post
				("/api/products").contentType(MediaType.APPLICATION_JSON)
				.content(productrequestString))
				.andExpect(status().isCreated());
		Assert.assertEquals("1", productRepository);
	}

	/*
	void getProductInstance() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productrequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.get(
								("/api/products").contentType(MediaType.APPLICATION_JSON)
						.content(productrequestString))
				.andExpect(status().isFound()));
		Assert.assertEquals("1", productRepository);
	}
	*/



	private ProductRequest getProductRequest() {
	return 	ProductRequest.builder()
				.name("Redmi")
				.description("Redmi note 11 pro")
				.price(BigDecimal.valueOf(2000.15))
				.build();

	}

}
