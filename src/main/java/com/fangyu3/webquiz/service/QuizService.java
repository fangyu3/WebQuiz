package com.fangyu3.webquiz.service;

import java.util.List;

import javax.validation.Valid;

import com.fangyu3.webquiz.entity.Quiz;

public interface QuizService {

	public List<Quiz> findAll();

	public Quiz findById(int quizId);

	public Quiz save(@Valid Quiz quiz);
	
	public void deleteById(int quizId);

}
