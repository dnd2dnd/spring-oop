package com.dnd.oop.member;

import org.springframework.http.HttpStatus;

import com.dnd.oop.exception.ErrorModel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorModel {
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 유저는 없습니다."), 
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다");

	private final HttpStatus httpStatus;
	private final String message;
}
