package com.qdb.app.users.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.model.UserResponseModel;
import com.qdb.app.users.service.UsersServiceInt;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private UsersServiceInt usersServiceInt;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetAllUsers() throws Exception {
		String getAllUsersURL = "/api/v1.0/users";

		UserResponseModel userResponse1 = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");
		UserResponseModel userResponse2 = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");
		List<UserResponseModel> userResponses = new ArrayList<>();
		userResponses.add(userResponse1);
		userResponses.add(userResponse2);

		when(usersServiceInt.getAllUsers()).thenReturn(userResponses);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getAllUsersURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		List<UserResponseModel> resultRespnse = mapFromJson(result.getResponse().getContentAsString(), List.class);
		assertNotNull(resultRespnse);
		assertEquals(2, resultRespnse.size());
	}

	@Test
	public void testGetUserByUserId() throws Exception {
		String getUserByUserId = "/api/v1.0/users/U999";

		
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");

		when(usersServiceInt.getUserByUserId(eq("U999"))).thenReturn(userResponse);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(getUserByUserId).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		UserResponseModel receivedUserResponse = mapFromJson(result.getResponse().getContentAsString(),
				UserResponseModel.class);
		assertNotNull(receivedUserResponse);
		assertEquals("U999", receivedUserResponse.getUserId());
	}

	@Test
	public void testCreateUser() throws Exception {
		String createUserURL = "/api/v1.0/users";
		
		UserRequestModel userRequest = new UserRequestModel("A999", "A999@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");

		String inputJson = mapToJson(userRequest);
				
		
		when(usersServiceInt.createUser(any())).thenReturn(userResponse);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(createUserURL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputJson))
				.andReturn();
		
		assertNotNull(result);
		assertEquals(201, result.getResponse().getStatus());
		UserResponseModel receivedUserResponse = mapFromJson(result.getResponse().getContentAsString(),
				UserResponseModel.class);
		assertNotNull(receivedUserResponse);
		assertEquals("U999", receivedUserResponse.getUserId());
	}

	@Test
	public void testUpdateUser() throws Exception {
		String updateUserURL = "/api/v1.0/users/U999";
		
		UserRequestModel userRequest = new UserRequestModel("A999", "A999@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");

		String inputJson = mapToJson(userRequest);
				
		
		when(usersServiceInt.updateUser(any(),eq("U999"))).thenReturn(userResponse);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(updateUserURL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputJson))
				.andReturn();
		
		assertNotNull(result);
		assertEquals(202, result.getResponse().getStatus());
		System.out.println(result.getResponse().getContentAsString());
		UserResponseModel receivedUserResponse = mapFromJson(result.getResponse().getContentAsString(),
				UserResponseModel.class);
		assertNotNull(receivedUserResponse);
		assertEquals("U999", receivedUserResponse.getUserId());
	}

	@Test
	public void testDeleteUserByUserId() throws Exception {
		String deleteUserByUserId = "/api/v1.0/users/U999";

		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");

		when(usersServiceInt.deleteUserByUserId(eq("U999"))).thenReturn(userResponse);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete(deleteUserByUserId).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		UserResponseModel receivedUserResponse = mapFromJson(result.getResponse().getContentAsString(),
				UserResponseModel.class);
		assertNotNull(receivedUserResponse);
		assertEquals("U999", receivedUserResponse.getUserId());
	}

	@Test
	public void testDeleteAllUsers() throws Exception {
		String deleteAllUsersURL = "/api/v1.0/users";

		when(usersServiceInt.deleteAllUsers()).thenReturn("Deleted All Users");

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete(deleteAllUsersURL).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		String receivedResponse = result.getResponse().getContentAsString();
		assertNotNull(receivedResponse);
		assertEquals("Deleted All Users", receivedResponse);

	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
