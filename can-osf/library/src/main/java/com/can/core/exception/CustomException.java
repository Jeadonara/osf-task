package com.can.core.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    private CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public static CustomException of(CustomError error, String... parameters) {
        String message = MessageFormat.format(error.getPattern(), (Object[]) parameters);
        return new CustomException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
