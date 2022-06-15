package com.hiremepls.turodemoproject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiremepls.turodemoproject.controllers.UserController;
import com.hiremepls.turodemoproject.models.Users;
import com.hiremepls.turodemoproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = {TuroDemoProjectMvcTests.Initializer.class})
class TuroDemoProjectMvcTests {
	@Container
	private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
			.withUsername("postgres")
			.withPassword("mysecretpassword")
			.withDatabaseName("turo");

	@Autowired
	private MockMvc mockMvc;

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
	public void createUser_whenPostMethod() throws Exception {
		Users user = new Users();
		user.setFirstName("Elon");
		user.setLastName("Must");
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(user)))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
	}

	public static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
