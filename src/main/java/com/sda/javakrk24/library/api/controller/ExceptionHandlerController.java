package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.exception.ErrorCode;
import com.sda.javakrk24.library.api.exception.LibraryAppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<LibraryAppException> handleException(){
        return handleAppException(new LibraryAppException(ErrorCode.GENERAL_ERROR));
    }

    @ExceptionHandler({ LibraryAppException.class })
    public ResponseEntity<LibraryAppException> handleAppException(LibraryAppException exception) {
        //mapowanie na dto z bledem
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    }
}
