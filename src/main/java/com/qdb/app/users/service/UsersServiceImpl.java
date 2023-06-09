package com.qdb.app.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qdb.app.users.entity.FileDataEntity;
import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.exception.UserException;
import com.qdb.app.users.model.FileDataResponseModel;
import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.model.UserResponseModel;
import com.qdb.app.users.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersServiceInt {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	
	public UsersServiceImpl() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Override
	public List<UserResponseModel> getAllUsers() {
		List<UserEntity> allUsers = usersRepository.findAll();
		if(null == allUsers) {
			throw new UserException("Users not found", HttpStatus.NOT_FOUND);
		}
		
		List<UserResponseModel> allUsersResponse = new ArrayList<>();
		UserResponseModel uer; 
		System.out.println("All users:"+allUsers.size());
		for(UserEntity ue : allUsers) {
			uer = modelMapper.map(ue, UserResponseModel.class);
			allUsersResponse.add(uer);	
		}
		
		return allUsersResponse;
	}

	@Override
	public UserResponseModel getUserByUserId(String userId) {
		Optional<UserEntity> user = usersRepository.findByUserId(userId);

		if(null == user || user.equals(Optional.empty())) {
			throw new UserException("User not found", HttpStatus.NOT_FOUND);
		}
		
		UserResponseModel userResponse = modelMapper.map(user, UserResponseModel.class);
		
		return userResponse;
	}

	@Override
	public UserResponseModel createUser(UserRequestModel createUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(createUser.getUserName());
		userEntity.setEmail(createUser.getEmail());
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setEncryptedPassword(passwordEncoder.encode(createUser.getPassword()));

		UserEntity createdUser = usersRepository.save(userEntity);

		if(null == createdUser) {
			throw new UserException("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		UserResponseModel createdUserResponse = modelMapper.map(createdUser, UserResponseModel.class);
		
		return createdUserResponse;
	}

	@Override
	public UserResponseModel updateUser(UserRequestModel updateUser, String userId) {
		Optional<UserEntity> userEntity = usersRepository.findByUserId(userId);
		UserEntity toUpdate = userEntity.get();
		
		
		if(null != toUpdate) {
			
			if(updateUser.getUserName() != "" && updateUser.getUserName()!= null) {
				if(!updateUser.getUserName().equals(toUpdate.getUserName()))
					toUpdate.setUserName(updateUser.getUserName());
			}
			
			if(updateUser.getEmail() != "" && updateUser.getEmail()!= null ) {
				if(!updateUser.getEmail().equals(toUpdate.getEmail()))
					toUpdate.setEmail(updateUser.getEmail());
			}
			
			if(updateUser.getPassword() != "" && updateUser.getPassword()!= null) {
				String encryptedPass = passwordEncoder.encode(updateUser.getPassword());
				System.out.println("#\n update user:"+encryptedPass);
				String existingPassword = toUpdate.getEncryptedPassword();
				System.out.println("#\n existing user:"+existingPassword);
			
				
				
				if(!encryptedPass.equalsIgnoreCase(existingPassword)) {
					
					System.out.println("#\n inside password update");
					toUpdate.setEncryptedPassword(passwordEncoder.encode(updateUser.getPassword()));
				}
					
			}
			UserEntity updatedUserEntity = usersRepository.save(toUpdate);
			UserResponseModel updatedUserResponse = modelMapper.map(updatedUserEntity, UserResponseModel.class);
			return updatedUserResponse;
		}
		else {
			throw new UserException("Failed to update user", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public String deleteAllUsers() {

		usersRepository.deleteAll();

		return "Deleted All Users";
	}

	@Override
	public UserResponseModel deleteUserByUserId(String userId) {
		Optional<UserEntity> userEntity = usersRepository.findByUserId(userId);

		UserResponseModel userResponse=null;
		
		if (null != userEntity) {
			userResponse = modelMapper.map(userEntity, UserResponseModel.class);
			usersRepository.delete(userEntity.get());
			return userResponse;
		} else {
			throw new UserException("User not found", HttpStatus.NOT_FOUND);
		}
	}

}
