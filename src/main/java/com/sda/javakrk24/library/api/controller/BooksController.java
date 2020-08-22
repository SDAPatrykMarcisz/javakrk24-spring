package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.dto.Book;
import com.sda.javakrk24.library.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private BookService bookService;

    @Autowired
    public BooksController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveBook(@RequestBody Book request){
        bookService.saveBook(request);
    }

}
