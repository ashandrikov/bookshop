package com.shandrikov.bookshop.DTOs;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login;
    private String password;
}
