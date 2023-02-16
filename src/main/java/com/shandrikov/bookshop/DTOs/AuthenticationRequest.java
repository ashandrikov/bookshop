package com.shandrikov.bookshop.DTOs;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AuthenticationRequest (
        @NotBlank(message = "Login must not be empty")
        @Length(min = 4, message = "Login is too short")
        @Length(max = 15, message = "Login is too long")
        String login,
        @NotBlank(message = "Password must not be empty")
        @Length(min = 4, message = "Password is too short")
        @Length(max = 15, message = "Password is too long")
        String password
){}
