package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.dto.Author;
import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.dto.BookResponse;
import com.sda.javakrk24.library.api.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksController.class)
@DisplayName("Books Controller Test")
@ActiveProfiles("test")
class BooksControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    private BookService bookService;

    @Mock
    Principal mockPrincipal;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .build();
        Mockito.when(mockPrincipal.getName()).thenReturn("1");
    }


    @Test
    void shouldSaveBookWhenAllDataProvided() throws Exception {
        //given
        Resource requestBody = new ClassPathResource("request/savebook.json");

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("1");

        //when
        mvc.perform(
                post("/api/books")
                        .header("content-type", "application/json")
                        .content(resourceToString(requestBody))
                        .principal(mockPrincipal))
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
                        .content(resourceToString(requestBody))
                        .principal(mockPrincipal))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<BookRequest> argumentCaptor = ArgumentCaptor.forClass(BookRequest.class);
        Mockito.verify(bookService).replaceBook(eq(expectedId), argumentCaptor.capture());
        assertEquals("jedyny prawdziwy testowy", argumentCaptor.getValue().getTitle());
        assertEquals(3, argumentCaptor.getValue().getAuthors().size());
        assertEquals("data", argumentCaptor.getValue().getIsbn());
        assertEquals(120, argumentCaptor.getValue().getPages());
        assertEquals(1992, argumentCaptor.getValue().getPublishYear());
    }

    @Test
    void shouldConvertToExceptionWhenServiceThrowRuntimeException() throws Exception {
        Resource requestBody = new ClassPathResource("request/savebook.json");

        doThrow(new RuntimeException()).when(bookService).replaceBook(any(), any());

        mvc.perform(
                put("/api/books/500")
                        .header("content-type", "application/json")
                        .content(resourceToString(requestBody))
                        .principal(mockPrincipal))
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

        mvc.perform(get("/api/books/10")
                .principal(mockPrincipal))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.title", equalTo("Alicja w krainie czarów")))
                .andExpect(jsonPath("$.authors", equalTo(Collections.EMPTY_LIST)))
                .andExpect(jsonPath("$.pages", equalTo(120)))
                .andExpect(jsonPath("$.publishYear", equalTo(1920)))
                .andExpect(jsonPath("$.isbn", equalTo("1234567890")));
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        List<BookResponse> bookResponseList = new ArrayList<>();
        BookResponse response1 = BookResponse.builder()
                .authors(Collections.singletonList(Author.builder()
                                .firstName("Jan")
                                .lastName("Kotek")
                                .birthYear(1965)
                                .description("Urodzony w żelazowej woli")
                                .build()
                        )
                )
                .title("Ala w kraju")
                .isbn("12344")
                .publishYear(1292)
                .pages(123)
                .build();
        BookResponse response2 = BookResponse.builder()
                .authors(Collections.emptyList())
                .title("Ewa w raju")
                .isbn("1234455")
                .publishYear(1292)
                .pages(123)
                .build();
        bookResponseList.add(response1);
        bookResponseList.add(response2);
        when(bookService.fetchAllBooks()).thenReturn(bookResponseList);
        mvc.perform(get("/api/books")
                .principal(mockPrincipal))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].title", equalTo("Ala w kraju")))
                .andExpect(jsonPath("$[0].authors.size()", equalTo(1)))
                .andExpect(jsonPath("$[0].pages", equalTo(123)))
                .andExpect(jsonPath("$[1].title", equalTo("Ewa w raju")));
    }

    private String resourceToString(Resource requestBody) throws IOException {
        return new Scanner(requestBody.getInputStream(), StandardCharsets.UTF_8)
                .useDelimiter("\\A").next();
    }


}