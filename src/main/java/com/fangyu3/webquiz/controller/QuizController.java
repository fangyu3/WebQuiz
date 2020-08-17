package com.fangyu3.webquiz.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fangyu3.webquiz.entity.Quiz;
import com.fangyu3.webquiz.exception.QuizNotFoundException;
import com.fangyu3.webquiz.quiz.QuizAnswer;
import com.fangyu3.webquiz.quiz.QuizResponse;

@RestController
@RequestMapping("/api")
public class QuizController {
	
	private List<Quiz> quizzes;
	private int quizNum;
	
	@PostConstruct
	private void setup() {
		quizzes = new ArrayList<Quiz>();
		quizNum = 1;
	}
	
	@GetMapping("/quizzes")
	public List<Quiz> getQuiz() {
		return quizzes;
	}
	
	@GetMapping("/quizzes/{quizId}") 
	public Quiz getQuizById(@PathVariable("quizId") int quizId) {
		
		if(quizId > this.quizzes.size() || quizId < 1)
			throw new QuizNotFoundException("Quiz id: " + quizId + " not found!");
		
		 Quiz quiz = this.quizzes.get(quizId-1);
		
		return quiz;
	}
 

	@PostMapping("/quizzes")
	public Quiz createQuiz(@RequestBody Quiz quiz) {
		Quiz newQuiz = new Quiz(quizNum,quiz.getTitle(),quiz.getText(),quiz.getOptions(),quiz.getAnswer());
		quizzes.add(newQuiz);
		this.quizNum++;
		System.out.println(newQuiz);
		return newQuiz;
	}
	
	@PostMapping("/quizzes/{quizId}/solve")
	public QuizResponse solveQuiz(@PathVariable("quizId") int quizId, @RequestBody QuizAnswer answer) {
		
		if(quizId > this.quizzes.size() || quizId < 1)
			throw new QuizNotFoundException("Quiz id: " + quizId + " not found!");
		
		QuizResponse result;
		
		Quiz quiz = this.quizzes.get(quizId-1);
			
		if (answer.getAnswer() != quiz.getAnswer()) 
			result = new QuizResponse(false,"Wrong answer! Please, try again.");
		else
			result = new QuizResponse(true,"Congratulations, you're right!");
		
		return result;
	}

}
