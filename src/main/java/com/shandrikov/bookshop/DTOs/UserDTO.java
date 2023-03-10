package com.shandrikov.bookshop.DTOs;

import com.shandrikov.bookshop.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UserDTO {
    private String login;
    private String password;
    private Role role;
    private boolean accountNonLocked;
    private Collection<? extends GrantedAuthority> authorities;

    public boolean isAdmin() {
        return Role.ADMINISTRATOR.equals(role);
    }
}
