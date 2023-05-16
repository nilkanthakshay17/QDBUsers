package com.qdb.app.users.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_data")
public class UserEntity {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "userid")
	private String userId;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String encryptedPassword;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<FileDataEntity> files = new ArrayList<>();
	
	
	public UserEntity() {
	}

	public UserEntity(int id, String userId, String userName, String email, String encryptedPassword) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<FileDataEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileDataEntity> files) {
		this.files = files;
	}
	
	public void addFile(FileDataEntity fileData) {
		this.files.add(fileData);
	}
}

