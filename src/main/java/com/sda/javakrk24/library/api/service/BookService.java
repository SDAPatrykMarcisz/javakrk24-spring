package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.dto.Book;
import com.sda.javakrk24.library.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookConverter converter;

    @Autowired
    public BookService (BookRepository bookRepository, BookConverter bookConverter){
        this.bookRepository = bookRepository;
        this.converter = bookConverter;
    }

    public void saveBook(Book request) {
        bookRepository.save(converter.fromDto(request));
    }
}
