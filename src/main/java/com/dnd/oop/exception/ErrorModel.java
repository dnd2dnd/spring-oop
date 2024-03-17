package com.dnd.oop.exception;

import org.springframework.http.HttpStatus;

public interface ErrorModel {

    HttpStatus getHttpStatus();

    String getMessage();
}
