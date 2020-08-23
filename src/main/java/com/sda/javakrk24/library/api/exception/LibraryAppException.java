package com.sda.javakrk24.library.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
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
