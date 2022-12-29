package com.shandrikov.bookshop.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum Role implements GrantedAuthority {
    ADMINISTRATOR, USER, EDITOR;
    @Override
    public String getAuthority() {
        return name();
    }
    public static Set<String> getAllRolesStrings(){
        return Set.of(USER.toString(), EDITOR.toString(), ADMINISTRATOR.toString());
    }
}
