package com.qdb.app.users.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponseModel {

	private String userId;
	
	private String userName;

	private String email;

	private String encryptedPassword;

	private List<FileDataResponseModel> files = new ArrayList<>();
	
	public UserResponseModel() {

	}
	
	public UserResponseModel(String userId, String userName, String email, String encryptedPassword,List<FileDataResponseModel> files) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.files = files;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public List<FileDataResponseModel> getFiles() {
		return files;
	}

	public void setFiles(List<FileDataResponseModel> files) {
		this.files = files;
	}
	
	public void addFile(FileDataResponseModel fder) {
		this.files.add(fder);
	}
}
