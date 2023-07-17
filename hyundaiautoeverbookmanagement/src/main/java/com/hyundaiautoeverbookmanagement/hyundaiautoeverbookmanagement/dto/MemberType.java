package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import org.springframework.security.core.GrantedAuthority;

public enum MemberType implements GrantedAuthority {
    // Member types
    ADMIN, MEMBER;

    @Override
    public String getAuthority() {
        return name();
    }
}