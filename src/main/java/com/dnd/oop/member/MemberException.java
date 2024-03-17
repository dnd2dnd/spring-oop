package com.dnd.oop.member;

import com.dnd.oop.exception.BusinessException;

public class MemberException extends BusinessException {
	public MemberException(MemberErrorCode errorModel) {
		super(errorModel);
	}
}
