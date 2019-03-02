package com.can.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class APIErrorHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res, Exception ex) throws IOException {
        if (ex instanceof CustomException)
            res.sendError(((CustomException) ex).getHttpStatus().value(), ex.getMessage());
        else
            res.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid Request");
    }

}
