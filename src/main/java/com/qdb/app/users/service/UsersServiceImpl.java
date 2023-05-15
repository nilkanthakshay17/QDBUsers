package com.qdb.app.users.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qdb.app.users.entity.UserEntity;
import com.qdb.app.users.model.UserRequestModel;
import com.qdb.app.users.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersServiceInt {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<UserEntity> getAllUsers() {
		List<UserEntity> allUsers = usersRepository.findAll();

		// handle Exception here

		return allUsers;
	}

	@Override
	public UserEntity getUserByUserId(String userId) {
		Optional<UserEntity> user = usersRepository.findByUserId(userId);

		if (null != user) {
			return user.get();
		} else {
			return null;
		}
	}

	@Override
	public UserEntity createUser(UserRequestModel createUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(createUser.getUserName());
		userEntity.setEmail(createUser.getEmail());
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setEncryptedPassword(passwordEncoder.encode(createUser.getPassword()));

		UserEntity createdUser = usersRepository.save(userEntity);

		return createdUser;
	}

	@Override
	public UserEntity updateUser(UserRequestModel updateUser, String userId) {
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
			
			return usersRepository.save(toUpdate);
		}
		else {
			return null;
		}
	}

	@Override
	public String deleteAllUsers() {

		usersRepository.deleteAll();

		return "Deleted All Users";
	}

	@Override
	public UserEntity deleteUserByUserId(String userId) {
		Optional<UserEntity> userEntity = usersRepository.findByUserId(userId);

		if (null != userEntity) {
			usersRepository.deleteById(userEntity.get().getId());
			return userEntity.get();
		} else {
			return null;
		}
	}

}
