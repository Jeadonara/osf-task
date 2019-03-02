package com.can.core.exception;

public class CustomError {

    public static CustomError GENERIC_ERROR = new CustomError("message: {0} ");
    public static CustomError INVALID_PARAMETER = new CustomError(" {0} can not be null or empty ");
    public static CustomError RECORD_NOT_FOUND = new CustomError(" {0} record not found, where  {1} = {2} ");

    private final String pattern;

    private CustomError(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
