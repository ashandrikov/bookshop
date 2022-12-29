package com.shandrikov.bookshop.domains;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login;
    private String password;
}
