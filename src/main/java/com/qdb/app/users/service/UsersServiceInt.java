package com.qdb.app.users.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.model.UserResponseModel;

@Service
public interface UsersServiceInt {

	public List<UserResponseModel> getAllUsers();
	public UserResponseModel getUserByUserId(String userId);
	public UserResponseModel createUser(UserRequestModel createUser);
	public UserResponseModel updateUser(UserRequestModel updateUser, String userId);
	public String deleteAllUsers();
	public UserResponseModel deleteUserByUserId(String userId);
}
