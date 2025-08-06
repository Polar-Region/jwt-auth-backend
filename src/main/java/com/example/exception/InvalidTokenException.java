package com.example.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends RuntimeException implements BaseHttpException {
    public InvalidTokenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
