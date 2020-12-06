package com.fangyu3.webquizengine.service;

import com.fangyu3.webquizengine.domain.Quiz;
import com.fangyu3.webquizengine.domain.User;
import com.fangyu3.webquizengine.domain.UserCompletion;
import com.fangyu3.webquizengine.mappers.QuizMapper;
import com.fangyu3.webquizengine.mappers.UserCompletionMapper;
import com.fangyu3.webquizengine.repositories.QuizRepository;
import com.fangyu3.webquizengine.repositories.UserCompletionRepository;
import com.fangyu3.webquizengine.repositories.UserRepository;
import com.fangyu3.webquizengine.web.model.QuizDto;
import com.fangyu3.webquizengine.web.model.UserCompletionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserCompletionRepository userCompletionRepository;
    private final QuizMapper quizMapper;
    private final UserCompletionMapper userCompletionMapper;
    private final int PAGE_SIZE = 10;


    @Override
    public QuizDto saveQuiz(QuizDto quizDto, Principal principal) {

        User user = userRepository.findByEmail(principal.getName()).get();

        Quiz newQuiz = quizMapper.quizDtoToQuiz(quizDto);

        newQuiz.setUser(user);

        user.getQuizzes().add(newQuiz);

        Quiz savedQuiz = quizRepository.save(newQuiz);

        return quizMapper.quizToQuizDto(savedQuiz);
    }

    @Override
    public QuizDto getQuizById(int id) {
        return quizMapper.quizToQuizDto(quizRepository.findById(id).orElse(null));
    }

    @Override
    public Page<QuizDto> getAllQuizzes(int pageNo) {

        Pageable paging = PageRequest.of(pageNo,PAGE_SIZE);
        Page<Quiz> foundQuizzes = quizRepository.findAll(paging);

        return foundQuizzes.map(quiz->quizMapper.quizToQuizDto(quiz));
    }

    @Override
    public boolean solveQuiz(int id, Integer[] userAnswer, Principal principal) {

        Quiz quiz = quizRepository.findById(id).get();

        Integer[] correctAnswer = quiz.getAnswer();

        if (correctAnswer == null) {
            if (userAnswer == null || userAnswer.length == 0) {
                saveUserCompletionRecord(principal,quiz);
                return true;
            }

            return false;
        }

        if (userAnswer == null) {
            if (correctAnswer == null || correctAnswer.length == 0) {
                saveUserCompletionRecord(principal,quiz);
                return true;
            }

            return false;
        }

        if (correctAnswer.length != userAnswer.length)
            return false;

        if (!List.of(correctAnswer).containsAll(List.of(userAnswer)))
            return false;

        // Successfully solved the quiz

        saveUserCompletionRecord(principal,quiz);

        return true;
    }

    private void saveUserCompletionRecord(Principal principal, Quiz completedQuiz) {
        User user = userRepository.findByEmail(principal.getName()).get();

        UserCompletion userCompletion = UserCompletion.builder()
                .quizId(completedQuiz.getId())
                .user(user)
                .build();

        user.getCompletions().add(userCompletion);

        userCompletionRepository.save(userCompletion);
    }


    @Override
    public boolean deleteQuiz(int id, Principal principal) {
        Quiz quiz = quizRepository.findById(id).get();

        if (!quiz.getUser().getEmail().equals(principal.getName()))
            return false;

        quizRepository.delete(quiz);

        return true;
    }

    @Override
    public Page<UserCompletionDto> getUserCompletedQuizzes(int pageNo, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();

        Pageable paging = PageRequest.of(pageNo,PAGE_SIZE, Sort.by("completedAt").descending());

        Page<UserCompletion> foundUserCompletions = userCompletionRepository.findAllByUser(user.getId(),paging);

        return foundUserCompletions.map(c -> userCompletionMapper.userCompletionToUserCompletionDto(c));
    }
}
