package com.nthao.nedcine.contants;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
