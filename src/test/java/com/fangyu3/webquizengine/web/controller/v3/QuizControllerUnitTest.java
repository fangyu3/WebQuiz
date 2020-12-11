package com.fangyu3.webquizengine.web.controller.v3;

import com.fangyu3.webquizengine.domain.Quiz;
import com.fangyu3.webquizengine.service.QuizService;
import com.fangyu3.webquizengine.web.controller.QuizController;
import com.fangyu3.webquizengine.web.model.QuizDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
@Disabled
class QuizControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuizService quizService;

    @Test
    void createQuiz() throws Exception {

        given(quizService.saveQuiz(any(),any())).willReturn(QuizDto.builder().build());

        String quizDtoJson = "{" +
                "\"title\": \"The Java Logo\"," +
                "\"text\": \"What is depicted on the Java logo?\"," +
                "\"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]," +
                "\"answer\":[2]" +
                "}";

        mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(quizDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void createQuizWithBadParameter() throws Exception {

        given(quizService.saveQuiz(any(),any())).willReturn(QuizDto.builder().build());

        String quizDtoJson = "{" +
                "\"title\": \"The Java Logo\"," +
                "\"text\": \"What is depicted on the Java logo?\"," +
                "\"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]," +
                "\"answer\":[2]" +
                "}";

        mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(quizDtoJson))
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createQuizWithBadParameter2() throws Exception {

        given(quizService.saveQuiz(any(),any())).willReturn(QuizDto.builder().build());

        String quizDtoJson = "{" +
                "\"title\": \"The Java Logo\"," +
                "\"text\": \"What is depicted on the Java logo?\"," +
                "\"options\":null" +
                "}";

        mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(quizDtoJson))
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void solveQuiz() {
    }
}