package com.mperezi.springtasksapp.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {
    private final LocalDateTime timestamp;
    private final HttpStatus status;
    private String message;

    public ApiError(HttpStatus status) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = ex.getMessage();
    }
}
