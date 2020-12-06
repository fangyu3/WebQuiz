package com.fangyu3.webquizengine.repositories;

import com.fangyu3.webquizengine.domain.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz,Integer> {
}
