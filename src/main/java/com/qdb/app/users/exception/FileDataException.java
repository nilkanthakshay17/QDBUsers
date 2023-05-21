package com.qdb.app.users.exception;

import org.springframework.http.HttpStatus;

public class FileDataException extends RuntimeException{

	HttpStatus status;
	
	public FileDataException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
