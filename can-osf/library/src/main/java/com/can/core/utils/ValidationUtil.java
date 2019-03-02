package com.can.core.utils;

import com.can.core.exception.CustomError;
import com.can.core.exception.CustomException;

import static org.springframework.util.StringUtils.hasText;

public class ValidationUtil {

    private static final String VALID_EMAIL_ADDRESS_REGEX = "^([\\w-\\.]+){1,64}@([\\w-&&[^_]]+){2,255}(.[a-z]{2,}){1,2}$";

    private ValidationUtil() {
    }

    public static void validateNotNull(Object value, CustomError error, String... parameters) {
        if (value == null) {
            throw CustomException.of(error, parameters);
        }
    }

    public static void validateNumber(Long value, CustomError error, String... parameters) {
        if (!(value != null && value.compareTo(0L) > 0)) {
            throw CustomException.of(error, parameters);
        }
    }


    public static void assertTrue(Boolean value, CustomError error, String... parameters) {
        if (!Boolean.TRUE.equals(value)) {
            throw CustomException.of(error, parameters);
        }
    }

    public static void validateHasText(String value, CustomError error, String... parameters) {
        if (!(value != null && value.trim().length() > 0)) {
            throw CustomException.of(error, parameters);
        }
    }

    public static void validateEmailAddress(String emailAddress) {
        Boolean isValid = hasText(emailAddress) ? emailAddress.toLowerCase().matches(VALID_EMAIL_ADDRESS_REGEX) : Boolean.FALSE;
        assertTrue(isValid, CustomError.GENERIC_ERROR, "Invalid Email Address");
    }


}
