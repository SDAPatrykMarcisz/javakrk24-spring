package com.sda.javakrk24.library.api.controller;

import com.sda.javakrk24.library.api.exception.ErrorCode;
import com.sda.javakrk24.library.api.exception.ErrorResponse;
import com.sda.javakrk24.library.api.exception.LibraryAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exception){
        log.error(exception.toString());
        return handleAppException(new LibraryAppException(ErrorCode.GENERAL_ERROR));
    }

    @ExceptionHandler({ LibraryAppException.class })
    public ResponseEntity<ErrorResponse> handleAppException(LibraryAppException exception) {
        //mapowanie na dto z bledem
        ErrorResponse response = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .httpStatus(exception.getHttpStatus().value())
                .httpStatusMessage(exception.getHttpStatus().toString())
                .build();
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
