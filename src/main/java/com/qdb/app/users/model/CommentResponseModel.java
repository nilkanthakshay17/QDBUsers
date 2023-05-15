package com.qdb.app.users.model;

public class CommentResponseModel {

	private String commentId;

	private String postId;

	private String name;

	private String email;

	private String body;
	
	public CommentResponseModel() {
	}

	public CommentResponseModel(String commentId, String postId, String name, String email, String body) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.name = name;
		this.email = email;
		this.body = body;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
