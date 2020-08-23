package com.sda.javakrk24.library.api.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    final String code;
    final String message;
    final int httpStatus;
    final String httpStatusMessage;
}
