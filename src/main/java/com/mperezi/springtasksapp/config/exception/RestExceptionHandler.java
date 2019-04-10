package com.mperezi.springtasksapp.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
