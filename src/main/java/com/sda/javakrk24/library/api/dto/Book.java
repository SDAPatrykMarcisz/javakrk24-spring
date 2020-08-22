package com.sda.javakrk24.library.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Book {

    private String title;
    private String author;
    private int pages;
    private int publishYear;
    private String isbn;

}
