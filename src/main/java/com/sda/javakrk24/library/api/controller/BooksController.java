package com.sda.javakrk24.library.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/books")
public class BooksController {

    @PostMapping()
    public void saveBook(@RequestBody Book request){

    }

}
