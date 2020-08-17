package com.fangyu3.webquiz.exception;

public class QuizErrorResponse {
	private String message;
	private int status;
	private long timestamp;
	
	public QuizErrorResponse() {}
	
	public QuizErrorResponse(String message, int status, long timestamp) {
		this.setMessage(message);
		this.setStatus(status);
		this.setTimestamp(timestamp);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
