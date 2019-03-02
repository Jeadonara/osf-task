package com.can.core.security;

import org.springframework.security.core.GrantedAuthority;

final class AdminAuthority implements GrantedAuthority {

    private AdminAuthority() {
    }

    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }

    static AdminAuthority getInstance() {
        return new AdminAuthority();
    }
}
