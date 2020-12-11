package com.fangyu3.webquizengine.web.controller;

import com.fangyu3.webquizengine.web.model.QuizDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
class QuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getQuiz() throws Exception {
        MvcResult result =
                mockMvc.perform(get("/api/quiz")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                "{\"title\":\"The Java Logo\",\"text\":\"What is depicted on the Java logo?\",\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}");
    }

    @Test
    void answerQuiz() throws Exception {
        QuizDto quizDto = QuizDto.builder()
                            .answer(new Integer[] {2})
                            .build();

        MvcResult result =
                mockMvc.perform(post("/api/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":2}"))
                        .andExpect(status().isOk())
                        .andReturn();

        assertEquals(result.getResponse().getContentAsString(),"{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}");
    }
}