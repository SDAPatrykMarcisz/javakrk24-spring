package com.sda.javakrk24.library.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookResponse {

    private String title;
    private List<Author> authors;
    private int pages;
    private int publishYear;
    private String isbn;

}
