package com.fangyu3.webquiz.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.fangyu3.webquiz.entity.Quiz;

public interface QuizService {

	public Page<Quiz> findAll(Integer pageNo, Integer pageSize, String sortBy);

	public Quiz findById(int quizId);

	public Quiz save(@Valid Quiz quiz);
	
	public void deleteById(int quizId);

}
