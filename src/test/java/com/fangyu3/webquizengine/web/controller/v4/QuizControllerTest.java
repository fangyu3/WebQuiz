package com.fangyu3.webquizengine.web.controller.v4;

import com.fangyu3.webquizengine.config.security.UserDetailsServiceImpl;
import com.fangyu3.webquizengine.service.QuizService;
import com.fangyu3.webquizengine.web.controller.QuizController;
import com.fangyu3.webquizengine.web.model.QuizDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuizService quizService;

    private QuizDto quizDto;

    private String quizDtoJson;

    @BeforeEach
    public void setup() {
        quizDto =  QuizDto.builder()
                    .id(0)
                    .title("The Java Logo")
                    .text("What is depicted on the Java logo?")
                    .options(new String[] {"Robot","Tea leaf","Cup of coffee","Bug"})
                    .answer(new Integer[] {2})
                    .build();

        quizDtoJson = "{" +
                "\"id\":\"\"," +
                "\"title\": \"The Java Logo\"," +
                "\"text\": \"What is depicted on the Java logo?\"," +
                "\"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]," +
                "\"answer\":[2]" +
                "}";
    }


    @Test
    void getQuizById() throws Exception {

        given(quizService.getQuizById(anyInt())).willReturn(quizDto);

        ConstraintFields fields = new ConstraintFields(QuizDto.class);

        mockMvc.perform(get("/api/quizzes/{quizId}",quizDto.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(quizDto.getId())))
                .andExpect(jsonPath("$.title",is(quizDto.getTitle().toString())))
                .andDo(document("api/quiz-get",
                        pathParameters(
                            parameterWithName("quizId").description("id of quiz to get")
                        ),

                        responseFields(
                                fields.withPath("id").description("Id of quiz"),
                                fields.withPath("title").description("Title of quiz"),
                                fields.withPath("text").description("Description of quiz"),
                                fields.withPath("options").description("Answer selections of quiz")
                        )
                ));
    }

    @Test
    void createQuiz() throws Exception {

        given(quizService.saveQuiz(any(),any())).willReturn(quizDto);

        ConstraintFields fields = new ConstraintFields(QuizDto.class);

        mockMvc.perform(post("/api/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(quizDtoJson))
                        .andExpect(status().isCreated())
                        .andDo(document("api/quiz-create",
                                requestFields(
                                        fields.withPath("id").description("Id of quiz"),
                                        fields.withPath("title").description("Title of quiz"),
                                        fields.withPath("text").description("Description of quiz"),
                                        fields.withPath("options").description("Answer selections of quiz"),
                                        fields.withPath("answer").description("Correct answer(s) of quiz")
                                )));
    }

    @Test
    void getAllQuizzes() throws Exception {
        QuizDto quizDto1 = QuizDto.builder().title("test1").text("test1").build();
        QuizDto quizDto2 = QuizDto.builder().title("test2").text("test2").build();
        QuizDto quizDto3 = QuizDto.builder().title("test3").text("test3").build();

        List<QuizDto> quizDtoList = List.of(quizDto1,quizDto2,quizDto3);
        Pageable pageable = PageRequest.of(0,10);

        Page<QuizDto> page = new PageImpl<>(quizDtoList,pageable,quizDtoList.size());

        given(quizService.getAllQuizzes(anyInt())).willReturn(page);

        mockMvc.perform(get("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("api/quiz-get-all",
                        responseFields(
                                fieldWithPath("content[]").description("Returned quizzes"),
                                fieldWithPath("content[].id").ignored(),
                                fieldWithPath("content[].title").ignored(),
                                fieldWithPath("content[].text").ignored(),
                                fieldWithPath("content[].options").ignored(),
                                fieldWithPath("pageable").description("Paging information"),
                                fieldWithPath("pageable.*").ignored(),
                                fieldWithPath("pageable.*.*").ignored(),
                                fieldWithPath("last").ignored(),
                                fieldWithPath("totalPages").ignored(),
                                fieldWithPath("totalElements").ignored(),
                                fieldWithPath("size").ignored(),
                                fieldWithPath("number").ignored(),
                                fieldWithPath("sort.*").ignored(),
                                fieldWithPath("numberOfElements").ignored(),
                                fieldWithPath("first").ignored(),
                                fieldWithPath("empty").ignored()
                        )));
    }


    @Test
    void solveQuiz() throws Exception {
        given(quizService.solveQuiz(anyInt(),any(),any())).willReturn(true);

        given(quizService.getQuizById(anyInt())).willReturn(quizDto);

        ConstraintFields fields = new ConstraintFields(QuizDto.class);

        String answerDtoJson = "{\"answer\": [2]}";

        mockMvc.perform(post("/api/quizzes/{quizId}/solve",quizDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(answerDtoJson))
                .andExpect(status().isOk())
                .andDo(document("api/quiz-solve",
                        pathParameters(
                                parameterWithName("quizId").description("id of quiz to solve")
                        ),

                        requestFields(
                                fields.withPath("answer").description("User input answer of quiz")
                        )));
    }

    @Test
    void deleteQuiz() {
    }

    @Test
    void getAllCompletedQuizzes() {
    }

    private static class ConstraintFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstraintFields (Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                    .descriptionsForProperty(path),". ")));
        }
    }
}