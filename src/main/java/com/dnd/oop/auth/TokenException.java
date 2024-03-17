package com.dnd.oop.auth;

import com.dnd.oop.exception.BusinessException;
import com.dnd.oop.exception.ErrorModel;

public class TokenException extends BusinessException {
	public TokenException(ErrorModel errorModel) {
		super(errorModel);
	}
}
