package com.fangyu3.webquizengine.service;

import com.fangyu3.webquizengine.domain.UserCompletion;
import com.fangyu3.webquizengine.web.model.QuizDto;
import com.fangyu3.webquizengine.web.model.UserCompletionDto;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface QuizService {
    public QuizDto saveQuiz(QuizDto quizDto, Principal principal);

    public QuizDto getQuizById(int id);

    public Page<QuizDto> getAllQuizzes(int pageNo);

    public boolean solveQuiz(int id, Integer[] userAnswer, Principal principal);

    public boolean deleteQuiz(int id, Principal principal);

    public Page<UserCompletionDto> getUserCompletedQuizzes(int pageNo, Principal principal);
}
