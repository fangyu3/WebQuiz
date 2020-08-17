package com.fangyu3.webquiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuizExceptionHandler {

	public QuizExceptionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler
	public ResponseEntity<QuizErrorResponse> handleException(Exception e) {
		QuizErrorResponse error = new QuizErrorResponse(e.getMessage(),
														HttpStatus.BAD_REQUEST.value(),
														System.currentTimeMillis()
														);

		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<QuizErrorResponse> handleQuizNotFoundException(QuizNotFoundException e) {
		QuizErrorResponse error = new QuizErrorResponse(e.getMessage(),
														HttpStatus.NOT_FOUND.value(),
														System.currentTimeMillis()
														);
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}

}
