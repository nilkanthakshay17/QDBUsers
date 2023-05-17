package com.qdb.app.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.model.UserResponseModel;
import com.qdb.app.users.service.UsersServiceInt;

@RestController
@RequestMapping("/api/v1.0")
public class UsersController {

	@Autowired
	private UsersServiceInt usersServiceInt;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {

		List<UserResponseModel> allUsers = usersServiceInt.getAllUsers();

		return ResponseEntity.status(HttpStatus.OK).body(allUsers);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUserByUserId(@PathVariable(name = "userId") String userId) {

		UserResponseModel user = usersServiceInt.getUserByUserId(userId);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserRequestModel createUser) {

		UserResponseModel createdUser = usersServiceInt.createUser(createUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<?> updateUser(@RequestBody UserRequestModel updateUser, @PathVariable(name = "userId")String userId) {

		UserResponseModel updatedUser = usersServiceInt.updateUser(updateUser, userId);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
	}
	
	
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteAllUsers() {

		String deleteResult = usersServiceInt.deleteAllUsers();

		return ResponseEntity.status(HttpStatus.OK).body(deleteResult);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUserByUserId(@PathVariable(name = "userId")String userId) {

		UserResponseModel deletedUser = usersServiceInt.deleteUserByUserId(userId);

		return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
	}
}
