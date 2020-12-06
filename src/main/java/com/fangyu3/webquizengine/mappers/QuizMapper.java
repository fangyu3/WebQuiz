package com.fangyu3.webquizengine.mappers;

import com.fangyu3.webquizengine.domain.Quiz;
import com.fangyu3.webquizengine.web.model.QuizDto;
import org.mapstruct.Mapper;

@Mapper
public interface QuizMapper {
    public QuizDto quizToQuizDto(Quiz quiz);

    public Quiz quizDtoToQuiz(QuizDto quizDto);
}
