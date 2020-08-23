package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksController.class)
@DisplayName("Books Controller Test")
class BooksControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    private BookService bookService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .build();
    }


    @Test
    void shouldSaveBookWhenAllDataProvided() throws Exception {
        //given
        Resource requestBody = new ClassPathResource("request/savebook.json");

        //when
        mvc.perform(
                post("/api/books")
                        .header("content-type", "application/json")
                        .content(resourceToString(requestBody)))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<BookRequest> argumentCaptor = ArgumentCaptor.forClass(BookRequest.class);
        Mockito.verify(bookService).saveBook(argumentCaptor.capture());
        assertEquals("jedyny prawdziwy testowy", argumentCaptor.getValue().getTitle());
        assertEquals("jedyny prawdziwy testowy", argumentCaptor.getValue().getTitle());
        assertEquals("jedyny prawdziwy testowy", argumentCaptor.getValue().getTitle());
        assertEquals("jedyny prawdziwy testowy", argumentCaptor.getValue().getTitle());

    }

    private String resourceToString(Resource requestBody) throws IOException {
        return new Scanner(requestBody.getInputStream(), StandardCharsets.UTF_8)
                .useDelimiter("\\A").next();
    }


}