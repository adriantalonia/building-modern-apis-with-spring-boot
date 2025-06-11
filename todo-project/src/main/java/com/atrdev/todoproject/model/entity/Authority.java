package com.atrdev.todoproject.model.entity;

import jakarta.persistence.Embeddable;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
public class Authority implements GrantedAuthority {
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority() {}

    @Override
    public String getAuthority() {
        return authority;
    }
}
