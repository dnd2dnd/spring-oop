package com.dnd.oop.auth;

import org.springframework.http.HttpStatus;

import com.dnd.oop.exception.ErrorModel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorModel {
	TOKEN_INVALID(HttpStatus.FORBIDDEN, "잘못된 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다"),
	TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 누락되었습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
