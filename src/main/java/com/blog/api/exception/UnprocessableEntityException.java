package com.blog.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException{
    private static final Long serialVersionUID=-2927762559336817363L;


    public UnprocessableEntityException(String errorMessage) {
        super(errorMessage);
    }
}

