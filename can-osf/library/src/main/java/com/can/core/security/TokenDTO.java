package com.can.core.security;

import java.util.Date;

public class TokenDTO {

    private final String value;
    private final Date expiresAt;

    public TokenDTO(String value, Date expiresAt) {
        this.value = value;
        this.expiresAt = expiresAt;
    }

    public String getValue() {
        return value;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }
}
