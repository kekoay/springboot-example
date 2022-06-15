package com.hiremepls.turodemoproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiremepls.turodemoproject.controllers.UserController;
import com.hiremepls.turodemoproject.models.Users;
import com.hiremepls.turodemoproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {TuroDemoProjectApplicationTests.Initializer.class})
class TuroDemoProjectApplicationTests {
	@Container
	private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
			.withUsername("postgres")
			.withPassword("mysecretpassword")
			.withDatabaseName("turo");

	@Autowired
	UserRepository repository;

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + container.getJdbcUrl(),
					"spring.datasource.username=" + container.getUsername(),
					"spring.datasource.password=" + container.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Test
	void addNewUser(){
		Users user = new Users("Elon", "Musk");
		Users savedUser = repository.save(user);
		List<Users> userList = repository.findAll();

		assertEquals(1, userList.size());
	}

	public static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
