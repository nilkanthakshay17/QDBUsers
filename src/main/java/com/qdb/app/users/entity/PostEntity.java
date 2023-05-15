package com.qdb.app.users.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts_data")
public class PostEntity {

	@Id
	@GeneratedValue
	private int id;
	
	private String postId;
	
	private String fileId;
	
	private String userId;
	
	private String title;
	
	private String body;

	@OneToMany
	private List<CommentEntity> comments;
	
	public PostEntity() {
		comments = new ArrayList<>();
	}

	public PostEntity(int id, String postId,String fileId, String userId, String title, String body) {
		super();
		this.id = id;
		this.postId = postId;
		this.fileId = fileId;
		this.userId = userId;
		this.title = title;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public String getFileId() {
		return fileId;
	}
	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
