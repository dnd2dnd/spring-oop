package com.dnd.oop.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorModel errorModel;

    public BusinessException(ErrorModel errorModel) {
        super(errorModel.getMessage());
        this.errorModel = errorModel;
    }
}
