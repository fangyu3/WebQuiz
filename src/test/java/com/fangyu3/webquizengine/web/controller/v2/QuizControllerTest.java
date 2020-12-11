package com.fangyu3.webquizengine.web.controller.v2;

import com.fangyu3.webquizengine.service.QuizService;
import com.fangyu3.webquizengine.web.controller.QuizController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class QuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void createQuiz() throws Exception {

        String quizDtoJson = "{" +
                "\"title\": \"The Java Logo\"," +
                "\"text\": \"What is depicted on the Java logo?\"," +
                "\"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]," +
                "\"answer\":2" +
                "}";

        MvcResult result = mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(quizDtoJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                "{\"id\":1,\"title\":\"The Java Logo\",\"text\":\"What is depicted on the Java logo?\",\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}");
    }

    @Test
    @Order(2)
    void getQuizById() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/quizzes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                "{\"id\":1,\"title\":\"The Java Logo\",\"text\":\"What is depicted on the Java logo?\",\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}");
    }

    @Test
    @Order(3)
    void createQuizAgain() throws Exception {

        String quizDtoJson = "{" +
                "\"title\":\"The Ultimate Question\"," +
                "\"text\":\"What is the answer to the Ultimate Question of Life, the Universe and Everything?\"," +
                "\"options\":[\"Everything goes right\",\"42\",\"2+2=4\",\"11011100\"]," +
                "\"answer\":2" +
                "}";

        MvcResult result = mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(quizDtoJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "{" +
                "\"id\":2," +
                "\"title\":\"The Ultimate Question\"," +
                "\"text\":\"What is the answer to the Ultimate Question of Life, the Universe and Everything?\"," +
                "\"options\":[\"Everything goes right\",\"42\",\"2+2=4\",\"11011100\"]" +
                "}");
    }

    @Test
    @Order(4)
    void getAllQuizzes() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "[{\"id\":1,\"title\":\"The Java Logo\",\"text\":\"What is depicted on the Java logo?\",\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]},{" +
                "\"id\":2," +
                "\"title\":\"The Ultimate Question\"," +
                "\"text\":\"What is the answer to the Ultimate Question of Life, the Universe and Everything?\"," +
                "\"options\":[\"Everything goes right\",\"42\",\"2+2=4\",\"11011100\"]" +
                "}]");
    }

    @Test
    @Order(5)
    void solveQuiz() throws Exception {

        MvcResult result = mockMvc.perform(post("/api/quizzes/1/solve")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"answer\":2}"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),"{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}");
    }
}