package com.fangyu3.webquiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangyu3.webquiz.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}
