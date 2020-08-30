package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.dto.BookResponse;
import com.sda.javakrk24.library.api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/***
 * Klasy typu Controller są pierwszymi po filtrach, które mapują żądanie http na konkretną metodę,
 * uwzględniają zarówno ścieżkę, jak i metodę czyli jeżeli posiadmy tylko mapowanie na metodę GET,
 * to wysyłając metodę HTTP Post, nie zostanie dopasowana do niej metoda przypisana GET-owi
 * RestController - stereotyp, adnotacja "dziedziczaca" po @Component, od @Controller rozni sie tym,
 * ze automatycznie mapuje na obiekty typu json, podczas gdy @Controller probuje wykorzystac silnik
 * szablonow do wygenerowania odpowiedzi
 *
 * RequestMapping - adnotacja pozwalajaca na mapowanie pomiedzy adresem zapytania, a metoda (java) która je obsluguje.
 * Moze byc stosowana zarowno na klasie controllera jak i na metodzie, koncowe mapowanie to polaczenie mapowania nad klasa
 * i nad metoda, np. nad klasa "/api/books", nad metoda "/{id}, ostatecznie obsluguje zadanie "/api/books/{id}
 */
@RestController
@RequestMapping("/api/books")
@Slf4j
public class BooksController {

    private BookService bookService;

    /**
     * mechanizm wstrzykniecia poprzez konstruktor, mechanizm dependency injection szuka odpowiadajacego
     * beanu w kontekscie, jesli zostaje znaleziony zostaje przekazany jako parametr konstruktora
     * @param bookService
     */
    @Autowired
    public BooksController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveBook(@RequestBody BookRequest request, Principal principal){
        log.info(principal.toString());
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
