package com.fangyu3.webquiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fangyu3.webquiz.entity.Quiz;
import com.fangyu3.webquiz.response.QuizAnswer;
import com.fangyu3.webquiz.response.QuizResponse;

@RestController
@RequestMapping("/api")
public class QuizController {
	
	@GetMapping("/quiz")
	public Quiz getQuiz() {
		return new Quiz("The Java Logo",
						"What is depicted on the Java logo?",
						new String[] {"Robot","Tea leaf","Cup of coffee","Bug"}
						);
	}
	
	@PostMapping("/quiz")
	public QuizResponse showResult(@RequestBody QuizAnswer answer) {
		
		QuizResponse result;
		
		if (answer.getAnswer() != 2) 
			result = new QuizResponse(false,"Wrong answer! Please, try again.");
		else
			result = new QuizResponse(true,"Congratulations, you're right!");
		
		return result;
	} 

}
