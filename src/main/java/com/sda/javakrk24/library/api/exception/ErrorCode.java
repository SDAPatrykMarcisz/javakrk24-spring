package com.sda.javakrk24.library.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    GENERAL_ERROR("LA1", "Wystąpił nieoczekiwany błąd", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHOR_NOT_FOUND("LA2", "Autor o zadanym ID nie został odnaleziony", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND("LA3", "Książka o zadanym ID nie została odnaleziona", HttpStatus.BAD_REQUEST);

    final String code;
    final String message;
    final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
