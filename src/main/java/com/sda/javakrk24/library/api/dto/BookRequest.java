package com.sda.javakrk24.library.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookRequest {

    private String title;
    private List<Long> authors;
    private int pages;
    private int publishYear;
    private String isbn;

}
