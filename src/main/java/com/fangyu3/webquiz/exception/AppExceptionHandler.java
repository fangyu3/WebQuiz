package com.fangyu3.webquiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

	public AppExceptionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse error = new ErrorResponse(e.getMessage(),
														HttpStatus.BAD_REQUEST.value(),
														System.currentTimeMillis()
														);

		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleQuizNotFoundException(QuizNotFoundException e) {
		ErrorResponse error = new ErrorResponse(e.getMessage(),
														HttpStatus.NOT_FOUND.value(),
														System.currentTimeMillis()
														);
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException e) {
		ErrorResponse error = new ErrorResponse(e.getMessage(),
														HttpStatus.BAD_REQUEST.value(),
														System.currentTimeMillis()
														);
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

}
