package com.dnd.oop.auth;

import com.dnd.oop.exception.BusinessException;
import com.dnd.oop.exception.ErrorModel;

public class TokenExpiredException extends BusinessException {
	public TokenExpiredException(AuthErrorCode authErrorCode) {
		super(authErrorCode);
	}
}
