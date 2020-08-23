package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.dao.BookEntity;
import com.sda.javakrk24.library.api.dto.BookResponse;
import com.sda.javakrk24.library.api.exception.LibraryAppException;
import com.sda.javakrk24.library.api.external.google.books.GoogleBooksApiClient;
import com.sda.javakrk24.library.api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookConverter bookConverter;
    @Mock
    private GoogleBooksApiClient booksApiClient;

    private BookService bookService;

    @BeforeEach
    void setUp(){
        bookService = new BookService(bookRepository, bookConverter, booksApiClient);
    }

    @Test
    void shouldReturnBookResponseById(){
        //given
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(100L);

        when(bookRepository.findById(100L)).thenReturn(Optional.of(bookEntity));

        BookResponse bookResponse = BookResponse.builder().title("Ania z zielonego").build();
        when(bookConverter.fromDao(bookEntity)).thenReturn(bookResponse);

        //when
        BookResponse bookById = bookService.getBookById(100L);

        //then
        assertEquals("Ania z zielonego", bookById.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenMissingBookById(){

        long id = 10L;

        when(bookRepository.findById(id))
                .thenReturn(Optional.empty());

        LibraryAppException libraryAppException = assertThrows(LibraryAppException.class,
                () -> bookService.getBookById(id));
        assertEquals("LA3", libraryAppException.getErrorCode());
    }

}