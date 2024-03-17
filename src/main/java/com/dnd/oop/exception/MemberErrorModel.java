package com.dnd.oop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorModel implements ErrorModel {
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "Duplicated Member");

    private final HttpStatus httpStatus;
    private final String message;
}
