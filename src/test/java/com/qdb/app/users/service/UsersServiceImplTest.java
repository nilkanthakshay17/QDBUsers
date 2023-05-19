package com.qdb.app.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.exception.UserException;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.model.UserResponseModel;
import com.qdb.app.users.repository.UsersRepository;

@SpringBootTest
class UsersServiceImplTest {

	@Autowired
	private UsersServiceImpl usersServiceImpl;
	
	@MockBean
	private UsersRepository usersRepository;
	
	@MockBean
	ModelMapper modelMapper;
	
	UserEntity userEntity;
	
	FileDataEntity fileDataEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		userEntity = new UserEntity(1, "999", "Akshay999", "Akshay@gmail.com", "Akshay@123");
		fileDataEntity = new FileDataEntity("999", "file1", "pdf", null, null);
		userEntity.addFile(fileDataEntity);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	public void testGetAllUsers() {
		
		UserEntity userEntity1 = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		UserEntity userEntity2 = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		List<UserEntity> userEntities = new ArrayList<>();
		userEntities.add(userEntity1);
		userEntities.add(userEntity2);
		
		UserResponseModel userResponse1 = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");
		UserResponseModel userResponse2 = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");
		List<UserResponseModel> userResponses = new ArrayList<>();
		userResponses.add(userResponse1);
		userResponses.add(userResponse1);
		
		when(usersRepository.findAll()).thenReturn(userEntities);
		when(modelMapper.map(any(),eq(UserResponseModel.class))).thenReturn(userResponse1);
		
		List<UserResponseModel> receivedResponses = usersServiceImpl.getAllUsers();
		
		assertNotNull(receivedResponses);
		assertEquals(2, receivedResponses.size());
	}

	@Test
	public void testGetUserByUserId() {
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		
		when(modelMapper.map(any(),eq(UserResponseModel.class))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceImpl.getUserByUserId("U999");
		
		assertNotNull(receivedResponse);
	}
	
	@Test
	public void testCreateUser() {
		UserRequestModel userRequest = new UserRequestModel("A999","A999@gmail.com","A@123");
		
		UserEntity userEntity2 = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A@gmail.com", "A@123");
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		when(usersRepository.save(any())).thenReturn(userEntity2);
		when(modelMapper.map(any(),eq(UserResponseModel.class))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceImpl.createUser(userRequest);
		assertNotNull(receivedResponse);
	}
	
	@Test
	public void testCreateUser_failure() {
		UserRequestModel userRequest = new UserRequestModel("A999","A999@gmail.com","A@123");
		
		UserEntity userEntity2 = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A@gmail.com", "A@123");
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		when(usersRepository.save(any())).thenReturn(null);
		when(modelMapper.map(any(),eq(UserResponseModel.class))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceImpl.createUser(userRequest);
		assertNotNull(receivedResponse);
	}
	
	@Test
	public void testUpdateUser() {
		UserRequestModel userRequest = new UserRequestModel("A999","A999@gmail.com","A@123");
		
		UserEntity userEntity2 = new UserEntity(1, "U999", "A999", "A@gmail.com", "A@123");
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A@gmail.com", "A@123");
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		when(usersRepository.save(userEntity)).thenReturn(userEntity2);
		when(modelMapper.map(any(),eq(UserResponseModel.class))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceImpl.updateUser(userRequest, "U999");
		assertNotNull(receivedResponse);
	}
	
	@Test
	public void testDeleteUserByUserId() throws Exception{
		UserResponseModel userResponse = new UserResponseModel("U999", "A999", "A999@gmail.com", "A@123");
		when(usersRepository.findByUserId(eq("U999"))).thenReturn(Optional.of(userEntity));
		
		when(modelMapper.map(any(),eq(UserResponseModel.class))).thenReturn(userResponse);
		
		UserResponseModel receivedResponse = usersServiceImpl.deleteUserByUserId("U999");
		
		assertNotNull(receivedResponse);
	}
	
	
	@Test
	public void testDeleteAllUsers() {
		String receivedResponse = usersServiceImpl.deleteAllUsers();
		
		assertNotNull(receivedResponse);
		assertEquals("Deleted All Users", receivedResponse);
	}

}
