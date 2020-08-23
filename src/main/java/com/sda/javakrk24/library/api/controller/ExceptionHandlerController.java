package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.exception.LibraryAppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ LibraryAppException.class })
    public ResponseEntity<LibraryAppException> handleException(LibraryAppException exception) {
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }
}
