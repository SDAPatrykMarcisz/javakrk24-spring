package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.dao.BookEntity;
import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.dto.BookResponse;
import com.sda.javakrk24.library.api.dto.IdResponse;
import com.sda.javakrk24.library.api.exception.ErrorCode;
import com.sda.javakrk24.library.api.exception.LibraryAppException;
import com.sda.javakrk24.library.api.external.google.books.GoogleBooksApiClient;
import com.sda.javakrk24.library.api.repository.AuthorRepository;
import com.sda.javakrk24.library.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookConverter bookConverter;
    private final GoogleBooksApiClient booksApiClient;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookConverter bookConverter, GoogleBooksApiClient booksApiClient) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookConverter = bookConverter;
        this.booksApiClient = booksApiClient;
    }

    @Transactional
    public IdResponse saveBook(BookRequest request) {
        BookEntity save = bookRepository.save(bookConverter.fromDto(request));
        return IdResponse.builder().id(save.getId()).build();
    }

    public List<BookResponse> fetchAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookEntity -> bookConverter.fromDao(bookEntity))
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookConverter::fromDao)
                .orElseThrow(() -> new LibraryAppException(ErrorCode.BOOK_NOT_FOUND));
    }

    @Transactional
    public void replaceBook(Long id, BookRequest request) {
        BookEntity entityToReplace = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        entityToReplace.getAuthors().forEach(author -> author.getBooks().remove(entityToReplace));
        entityToReplace.setAuthors(authorRepository.findAllById(request.getAuthors()));
        entityToReplace.setTitle(request.getTitle());
        entityToReplace.setPages(request.getPages());
        entityToReplace.setPublishYear(request.getPublishYear());
        entityToReplace.setIsbn(request.getIsbn());

        authorRepository.findAllById(request.getAuthors())
                .forEach(author -> author.getBooks().add(entityToReplace));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookResponse findByIsbnExternal(String isbn) {
        booksApiClient.getBookByIsbn(isbn);
        //1. zapytanie do zewnetrznego serwisu
        //2. konwersja uzyskanych danych do naszej postaci
        //3. zwrocenie danych do controllera
        return null;
    }
}
