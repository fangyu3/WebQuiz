package com.fangyu3.webquiz.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fangyu3.webquiz.entity.Quiz;
import com.fangyu3.webquiz.exception.QuizNotFoundException;
import com.fangyu3.webquiz.quiz.QuizResponse;
import com.fangyu3.webquiz.quiz.UserQuizAnswer;
import com.fangyu3.webquiz.service.QuizService;

@RestController
@RequestMapping("/api")
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	@GetMapping("/quizzes")
	public List<Quiz> getQuiz() {
		return quizService.findAll();
	}
	
	@GetMapping("/quizzes/{quizId}") 
	public Quiz getQuizById(@PathVariable("quizId") int quizId) {
		
		Quiz quiz = quizService.findById(quizId);
		
		if(quiz == null)
			throw new QuizNotFoundException("Quiz id: " + quizId + " not found!");
		
		return quiz;
	}
 

	@PostMapping("/quizzes")
	public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
		
		Quiz newQuiz = quizService.save(quiz);

		return newQuiz;
	}
	
	@PostMapping("/quizzes/{quizId}/solve")
	public QuizResponse solveQuiz(@PathVariable("quizId") int quizId, @RequestBody UserQuizAnswer userResponse) {
		
		Quiz quiz = quizService.findById(quizId);
		
		if(quiz == null)
			throw new QuizNotFoundException("Quiz id: " + quizId + " not found!");
		
		
		int[] userAnswer = userResponse.getAnswer();
		int[] correctAnswer = quiz.getAnswer();
		int numCorrect = 0;
		
		if(userAnswer == null || correctAnswer == null) {
			
			if ((userAnswer == null && correctAnswer == null) 
					||(userAnswer == null && correctAnswer.length == 0) 
					||(correctAnswer == null && userAnswer.length == 0))
				return new QuizResponse(true,"Congratulations, you're right!");
		}
		else 
		{
			// Neither answer contains null 
			for (int idx:userAnswer) 
			{
				for(int correctIdx:correctAnswer) 
				{
					if(idx == correctIdx) 
						numCorrect++;
				}
			}
				
			if(numCorrect == correctAnswer.length && userAnswer.length == correctAnswer.length)
				return new QuizResponse(true,"Congratulations, you're right!");

		}
		
		return new QuizResponse(false,"Wrong answer! Please, try again.");
	}
	
	@DeleteMapping("/quizzes/{quizId}")
	public void deleteQuizById(@PathVariable("quizId") int quizId) {
		
	}

}
