package com.qdb.app.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QDBExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleUserException(UserException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(FileDataException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleFileDataException(FileDataException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(PostException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handlePostException(PostException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(CommentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleCommentException(CommentException exception) {
		return exception.getMessage();
	}
}
