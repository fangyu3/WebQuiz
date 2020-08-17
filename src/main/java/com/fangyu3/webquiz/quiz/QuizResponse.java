package com.fangyu3.webquiz.quiz;

public class QuizResponse {
	
	private Boolean success;
	private String feedback;

	public QuizResponse() {
		// TODO Auto-generated constructor stub
	}

	public QuizResponse(Boolean success, String feedback) {
		this.success = success;
		this.feedback = feedback;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
