package com.qdb.app.users.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.model.UserRequestModel;

@Service
public interface UsersServiceInt {

	public List<UserEntity> getAllUsers();
	public UserEntity getUserByUserId(String userId);
	public UserEntity createUser(UserRequestModel createUser);
	public UserEntity updateUser(UserRequestModel updateUser, String userId);
	public String deleteAllUsers();
	public UserEntity deleteUserByUserId(String userId);
}
