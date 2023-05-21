package com.qdb.app.users.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QDBExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Object> handleUserException(UserException exception) {
		return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
	}
	
	@ExceptionHandler(FileDataException.class)
	public ResponseEntity<Object> handleFileDataException(FileDataException exception) {
		return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
	}
	
	@ExceptionHandler(PostException.class)
	public ResponseEntity<Object> handlePostException(PostException exception) {
		return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
	}
	
	@ExceptionHandler(CommentException.class)
	public ResponseEntity<Object> handleCommentException(CommentException exception) {
		return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
	}
}
