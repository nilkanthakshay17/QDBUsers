package com.qdb.app.users.model;

import java.util.List;

public class PostResponseModel {

	private String postId;

	private String fileId;

	private String userId;

	private String title;

	private String body;

	
	public PostResponseModel() {
	}

	public PostResponseModel(String postId, String fileId, String userId, String title, String body
			) {
		super();
		this.postId = postId;
		this.fileId = fileId;
		this.userId = userId;
		this.title = title;
		this.body = body;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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

}
