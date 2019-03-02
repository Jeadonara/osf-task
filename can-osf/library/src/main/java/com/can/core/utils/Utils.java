package com.can.core.utils;

import java.time.LocalDate;
import java.util.UUID;

public class Utils {
    public String generateUUID(String userName) {
        String source = LocalDate.now().toString() + userName;
        byte[] bytes = source.getBytes();
        return UUID.nameUUIDFromBytes(bytes).toString();
    }

    public static <T> T nvl(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }


}
