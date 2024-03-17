package com.dnd.oop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getErrorModel().getHttpStatus())
            .body(new ErrorResponse(e.getErrorModel().getHttpStatus(), e.getMessage()));
    }

    record ErrorResponse(
       HttpStatus httpStatus,
       String message
    ) {
    }
}
