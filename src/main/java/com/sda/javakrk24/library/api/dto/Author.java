package com.sda.javakrk24.library.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Author {
    private String firstName;
    private String lastName;
    private String description;
    private long birthYear;
}
