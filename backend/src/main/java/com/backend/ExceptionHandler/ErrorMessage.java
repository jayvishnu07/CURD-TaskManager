package com.backend.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public class ErrorMessage {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
