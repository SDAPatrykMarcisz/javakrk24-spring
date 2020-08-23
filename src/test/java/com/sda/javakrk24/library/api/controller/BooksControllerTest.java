package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.dto.BookResponse;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        assertEquals(3, argumentCaptor.getValue().getAuthors().size());

    }

    @Test
    void shouldReplaceBookWhenAllDataProvided() throws Exception {
        //given
        Resource requestBody = new ClassPathResource("request/savebook.json");

        //when
        Long expectedId = 100l;
        mvc.perform(
                put("/api/books/" + expectedId)
                        .header("content-type", "application/json")
                        .content(resourceToString(requestBody)))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<BookRequest> argumentCaptor = ArgumentCaptor.forClass(BookRequest.class);
        Mockito.verify(bookService).replaceBook(eq(expectedId), argumentCaptor.capture());
        assertEquals("jedyny prawdziwy testowy", argumentCaptor.getValue().getTitle());
        assertEquals(3, argumentCaptor.getValue().getAuthors().size());
        assertEquals(3, argumentCaptor.getValue().getIsbn());
        assertEquals(3, argumentCaptor.getValue().getPages());
        assertEquals(3, argumentCaptor.getValue().getPublishYear());
    }

    @Test
    void shouldConvertToExceptionWhenServiceThrowRuntimeException() throws Exception {
        Resource requestBody = new ClassPathResource("request/savebook.json");

        doThrow(new RuntimeException()).when(bookService).replaceBook(any(), any());

        mvc.perform(
                put("/api/books/500")
                        .header("content-type", "application/json")
                        .content(resourceToString(requestBody)))
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.code", equalTo("LA1")))
                .andExpect(jsonPath("$.httpStatus", equalTo(500)))
                .andExpect(jsonPath("$.message", equalTo("Wystąpił nieoczekiwany błąd")))
                .andExpect(jsonPath("$.httpStatusMessage", equalTo("500 INTERNAL_SERVER_ERROR")));
    }

    @Test
    void shouldReturnResponseBookWhenIdFounded() throws Exception {
        //when
        BookResponse response = BookResponse.builder()
                .authors(Collections.emptyList())
                .title("Alicja w krainie czarów")
                .isbn("1234567890")
                .publishYear(1920)
                .pages(120)
                .build();
        when(bookService.getBookById(10L)).thenReturn(response);

        mvc.perform(get("/api/books/10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.title", equalTo("Alicja w krainie czarów")))
                .andExpect(jsonPath("$.authors", equalTo(Collections.EMPTY_LIST)))
                .andExpect(jsonPath("$.pages", equalTo(120)))
                .andExpect(jsonPath("$.publishYear", equalTo(1920)))
                .andExpect(jsonPath("$.isbn", equalTo("1234567890")));
    }

    private String resourceToString(Resource requestBody) throws IOException {
        return new Scanner(requestBody.getInputStream(), StandardCharsets.UTF_8)
                .useDelimiter("\\A").next();
    }


}