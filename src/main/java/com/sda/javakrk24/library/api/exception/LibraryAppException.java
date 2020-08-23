package com.sda.javakrk24.library.api.exception;

import org.springframework.http.HttpStatus;

public class LibraryAppException extends RuntimeException {

    private String errorCode;
    private String message;
    private HttpStatus httpStatus;

    public LibraryAppException(ErrorCode errorCode){
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
        this.httpStatus = errorCode.httpStatus;
    }

}
