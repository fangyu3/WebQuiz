package com.fangyu3.webquizengine.web.controller;

import com.fangyu3.webquizengine.domain.UserCompletion;
import com.fangyu3.webquizengine.service.QuizService;
import com.fangyu3.webquizengine.web.model.QuizDto;
import com.fangyu3.webquizengine.web.model.UserAnswerDto;
import com.fangyu3.webquizengine.web.model.UserCompletionDto;
import com.fangyu3.webquizengine.web.response.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable int id) {
        if (quizService.getQuizById(id) == null )
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity(quizService.getQuizById(id), HttpStatus.OK);
    }

    @PostMapping("/quizzes")
    public ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody QuizDto quizDto, Principal principal) {
        return new ResponseEntity(quizService.saveQuiz(quizDto,principal),HttpStatus.CREATED);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Page<QuizDto>> getAllQuizzes(@RequestParam(defaultValue = "0") int page) {
        return new ResponseEntity(quizService.getAllQuizzes(page),HttpStatus.OK);
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<Success> solveQuiz(@PathVariable int id, @Valid @RequestBody UserAnswerDto userAnswerDto, Principal principal) {

        if (quizService.getQuizById(id) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (quizService.solveQuiz(id, userAnswerDto.getAnswer(), principal))
            return new ResponseEntity(Success.builder().success(true).feedback("Congratulations, you're right!").build(),HttpStatus.OK);

        return new ResponseEntity(Success.builder().success(false).feedback("Wrong answer! Please, try again.").build(),HttpStatus.OK);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable int id, Principal principal) {

        if (quizService.getQuizById(id) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (!quizService.deleteQuiz(id, principal))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/quizzes/completed")
    public ResponseEntity<Page<UserCompletionDto>> getAllCompletedQuizzes(@RequestParam(defaultValue = "0") int page, Principal principal) {
        return new ResponseEntity(quizService.getUserCompletedQuizzes(page,principal), HttpStatus.OK);
    }
}
