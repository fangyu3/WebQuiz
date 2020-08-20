package com.fangyu3.webquiz.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangyu3.webquiz.dao.QuizRepository;
import com.fangyu3.webquiz.entity.Quiz;

@Service
public class QuizServiceImpl implements QuizService{

	@Autowired
	private QuizRepository quizRepository;
	
	@Override
	public List<Quiz> findAll() {
		return quizRepository.findAll();
	}

	@Override
	public Quiz findById(int quizId) {
		
		Optional<Quiz >result = quizRepository.findById(quizId);
		
		Quiz quiz = null;
		
		if (result.isPresent())
			quiz = result.get();
		
		return quiz; 
	}

	@Override
	public Quiz save(@Valid Quiz quiz) {
		return quizRepository.save(quiz);
	}
	
}
