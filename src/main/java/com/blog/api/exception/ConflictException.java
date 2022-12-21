package com.blog.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConflictException extends RuntimeException {
    private static final Long serialVersionUID = -2927762559336817363L;

    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
