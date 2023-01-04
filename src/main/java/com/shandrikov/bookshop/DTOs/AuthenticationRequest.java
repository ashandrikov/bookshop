package com.shandrikov.bookshop.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthenticationRequest {
    @NotBlank(message = "Login must not be empty")
    @Length(min = 4, message = "Login is too short")
    @Length(max = 15, message = "Login is too long")
    private String login;
    @NotBlank(message = "Password must not be empty")
    @Length(min = 4, message = "Password is too short")
    @Length(max = 15, message = "Password is too long")
    private String password;
}
