package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.dto.BookResponse;
import com.sda.javakrk24.library.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private BookService bookService;

    @Autowired
    public BooksController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveBook(@RequestBody BookRequest request){
        bookService.saveBook(request);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/external/{isbn}")
    public BookResponse searchByIsbnExternal(@PathVariable("isbn") String isbn){
        return bookService.findByIsbnExternal(isbn);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookResponse> getAllBooks(){
        return bookService.fetchAllBooks();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookResponse getBookById(@PathVariable("id") Long id){
        return bookService.getBookById(id);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void replaceBook(@PathVariable("id") Long id, @RequestBody BookRequest request){
        bookService.replaceBook(id, request);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
    }

}
