package com.qdb.app.users.model;

public class CommentRequestModel {
	
	private String body;
	
	public CommentRequestModel() {
	}

	public CommentRequestModel(String body) {
		super();
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
