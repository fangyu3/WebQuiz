package com.fangyu3.webquiz.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fangyu3.webquiz.dao.QuizRepository;
import com.fangyu3.webquiz.entity.Quiz;

@Service
public class QuizServiceImpl implements QuizService{

	@Autowired
	private QuizRepository quizRepository;
	
	@Override
	public Page<Quiz> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		return quizRepository.findAll(paging);
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

	@Override
	public void deleteById(int quizId) {
		quizRepository.deleteById(quizId);
		
	}
	
	
	
}
