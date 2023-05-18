package com.qdb.app.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.model.UserResponseModel;
import com.qdb.app.users.repository.UsersRepository;

@SpringBootTest
class UsersServiceIntTest {

	@MockBean
	private UsersServiceInt usersServiceInt;
	
	@MockBean
	private UsersRepository usersRepository;
	
	UserEntity userEntity;
	
	FileDataEntity fileDataEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		userEntity = new UserEntity(1, "999", "Akshay999", "Akshay@gmail.com", "Akshay@123");
		fileDataEntity = new FileDataEntity("999", "file1", "pdf", null, null);
		userEntity.addFile(fileDataEntity);
		
		Mockito.when(usersRepository.findByUserId("999")).thenReturn(Optional.of(userEntity));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testFindByUserId_success() {
		Optional<UserEntity> user = usersRepository.findByUserId("999");
		
		assertNotEquals(Optional.empty(), user);
		assertEquals("999", user.get().getUserId());
		assertEquals("Akshay999", user.get().getUserName());
		assertEquals("Akshay@gmail.com", user.get().getEmail());
		assertEquals("Akshay@123", user.get().getEncryptedPassword());
		assertNotNull(user.get().getFiles());
	}
	
	@Test
	public void testFindByUserId_failure() {
		Optional<UserEntity> user = usersRepository.findByUserId("111");
		
		assertEquals(Optional.empty(), user);
	
	}
	
	@Test
	public void testGetAllUsers() {
		List<UserResponseModel> userRepsonses = new ArrayList<>();
		UserResponseModel userResponse = new UserResponseModel("999", "A999", "A999@gmail.com", "A@123");
		FileDataResponseModel fileResponse = new FileDataResponseModel("999", "F999", "pdf");
		userResponse.addFile(fileResponse);
		
		userRepsonses.add(userResponse);
		
		when(usersServiceInt.getAllUsers()).thenReturn(userRepsonses);
		
		List<UserResponseModel> receivedResponses = usersServiceInt.getAllUsers();
		
		assertNotNull(receivedResponses);
		assertEquals(1, receivedResponses.size());
	}

	@Test
	public void testGetUserByUserId() {
		UserResponseModel userResponse = new UserResponseModel("999", "A999", "A999@gmail.com", "A@123");
		FileDataResponseModel fileResponse = new FileDataResponseModel("999", "F999", "pdf");
		userResponse.addFile(fileResponse);
		
		when(usersServiceInt.getUserByUserId(eq("999"))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceInt.getUserByUserId("999");
		
		assertNotNull(receivedResponse);
		assertEquals("999", receivedResponse.getUserId());
		assertEquals("A999", receivedResponse.getUserName());
		assertEquals("A999@gmail.com", receivedResponse.getEmail());
		assertEquals("A@123", receivedResponse.getEncryptedPassword());
		assertEquals(1, receivedResponse.getFiles().size());
	}
	
	@Test
	public void testCreateUser() {
		UserRequestModel userRequest = new UserRequestModel("A999", "A999@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("999", "A999", "A999@gmail.com", "A@123");
		
		
		when(usersServiceInt.createUser(eq(userRequest))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceInt.createUser(userRequest);
		
		assertNotNull(receivedResponse);
		assertEquals("999", receivedResponse.getUserId());
		assertEquals("A999", receivedResponse.getUserName());
		assertEquals("A999@gmail.com", receivedResponse.getEmail());
		assertEquals("A@123", receivedResponse.getEncryptedPassword());
		
	}
	
	@Test
	public void testUpdateUser() {
		UserRequestModel userRequest = new UserRequestModel("999", "A999@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("999", "A999", "A999@gmail.com", "A@123");
		
		
		when(usersServiceInt.updateUser(eq(userRequest),eq("999"))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceInt.updateUser(userRequest,"999");
		
		assertNotNull(receivedResponse);
		assertEquals("999", receivedResponse.getUserId());
		assertEquals("A999", receivedResponse.getUserName());
		assertEquals("A999@gmail.com", receivedResponse.getEmail());
		assertEquals("A@123", receivedResponse.getEncryptedPassword());		
	}
	
	@Test
	public void testDeleteUserByUserId() {
		UserResponseModel userResponse = new UserResponseModel("999", "A999", "A999@gmail.com", "A@123");
		
		
		when(usersServiceInt.deleteUserByUserId(eq("999"))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceInt.deleteUserByUserId("999");
		
		assertNotNull(receivedResponse);
		assertEquals("999", receivedResponse.getUserId());
		assertEquals("A999", receivedResponse.getUserName());
		assertEquals("A999@gmail.com", receivedResponse.getEmail());
		assertEquals("A@123", receivedResponse.getEncryptedPassword());
	}
	
	@Test
	public void testDeleteAllUsers() {
		
		
		when(usersServiceInt.deleteAllUsers()).thenReturn("Deleted All Users");
		
		String receivedResponse = usersServiceInt.deleteAllUsers();
		
		assertNotNull(receivedResponse);
		assertEquals("Deleted All Users", receivedResponse);
	}
}
