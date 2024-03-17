package com.dnd.oop.auth;

import com.dnd.oop.exception.BusinessException;

public class TokenException extends BusinessException {
	public TokenException(AuthErrorCode errorModel) {
		super(errorModel);
	}
}
