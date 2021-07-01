package com.example.conference;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.conference.controllers.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConferenceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private UserController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void checkGetAllUsers() throws Exception {
		this.mockMvc.perform(get("/api/user")).andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$[0].login").value("adam"))
			.andExpect(jsonPath("$[0].email").value("adam@email.com"))
			.andExpect(jsonPath("$[1].login").value("kasia"))
			.andExpect(jsonPath("$[1].email").value("kasia@email.com"))
			.andExpect(jsonPath("$[2].login").value("bartek"))
			.andExpect(jsonPath("$[2].email").value("bartek@email.com"));
	}

	@Test
	public void checkPostUser() throws Exception {
		String login = "login1";
		String email = "email@e";
		
		Map<String, String> user = new HashMap<>();
		user.put("login", login);
		user.put("email", email);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(user);

		this.mockMvc.perform(
			post("/api/user").contentType(MediaType.APPLICATION_JSON).content(requestJson)
			).andExpect(status().isCreated())
			.andExpect(jsonPath("$.login").value(login))
			.andExpect(jsonPath("$.email").value(email));
	}

}
