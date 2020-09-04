package com.fangyu3.webquiz.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fangyu3.webquiz.dao.CompletionRepository;
import com.fangyu3.webquiz.entity.Completion;
import com.fangyu3.webquiz.entity.Quiz;
import com.fangyu3.webquiz.entity.User;
import com.fangyu3.webquiz.exception.QuizNotFoundException;
import com.fangyu3.webquiz.exception.UserNotQuizAuthorException;
import com.fangyu3.webquiz.quiz.QuizResponse;
import com.fangyu3.webquiz.quiz.UserQuizAnswer;
import com.fangyu3.webquiz.service.QuizService;

@RestController
@RequestMapping("/api")
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private CompletionRepository completionRepository;
	
	@GetMapping("/quizzes")
	public Page<Quiz> getQuiz(@RequestParam(defaultValue = "1") Integer page) {
		return quizService.findAll(page-1,10,"id");
	}
	
	@GetMapping("/quizzes/{quizId}") 
	public Quiz getQuizById(@PathVariable("quizId") int quizId) {
		
		Quiz quiz = quizService.findById(quizId);
		
		if(quiz == null)
			throw new QuizNotFoundException("Quiz id: " + quizId + " not found!");
		
		return quiz;
	}
	
	@GetMapping("/quizzes/completed")
	public Page<Completion> getCompletedQuiz(@RequestParam(defaultValue = "1") Integer page,
											@Autowired Authentication authentication) {
		return completionRepository.findAllByUserEmail(((User)authentication.getPrincipal()).getEmail(), PageRequest.of(page-1,10));
	}
 

	@PostMapping("/quizzes")
	public Quiz createQuiz(@Valid @RequestBody Quiz quiz,@Autowired Authentication authentication) {
		
		quiz.setUser((User)(authentication.getPrincipal()));
		Quiz newQuiz = quizService.save(quiz);

		return newQuiz;
	}
	
	@PostMapping("/quizzes/{quizId}/solve")
	public QuizResponse solveQuiz(@PathVariable("quizId") int quizId, 
								@RequestBody UserQuizAnswer userResponse,
								@Autowired Authentication authentication) {
		
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
			{
				completionRepository.save(new Completion((User)authentication.getPrincipal(),quiz));
				return new QuizResponse(true,"Congratulations, you're right!");
			}
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
				
			if(numCorrect == correctAnswer.length && userAnswer.length == correctAnswer.length) {
				completionRepository.save(new Completion((User)authentication.getPrincipal(),quiz));
				return new QuizResponse(true,"Congratulations, you're right!");
			}
		}
		
		return new QuizResponse(false,"Wrong answer! Please, try again.");
	}
	
	@DeleteMapping("/quizzes/{quizId}")
	public Object deleteQuizById(@PathVariable("quizId") int quizId, @Autowired Authentication authentication) {
		
		Quiz quiz = quizService.findById(quizId);
		
		if(quiz == null)
			throw new QuizNotFoundException("Quiz id: " + quizId + " not found!");
						
		if (quiz.getUser().getEmail() != ((User)authentication.getPrincipal()).getEmail())
			throw new UserNotQuizAuthorException("You are not the author of quiz. Cannot delete");
		
		quizService.deleteById(quizId);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

}
