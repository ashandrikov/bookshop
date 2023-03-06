package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.services.AuthService;
import com.shandrikov.bookshop.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.shandrikov.bookshop.utils.StringPool.USER_EXISTS;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserServiceImpl userService;
    private final AuthService authService;

    @GetMapping("/login")
    public String getLoginForm(){
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationForm(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") AuthenticationRequest authRequest){
        if (userService.checkIfUsernameExists(authRequest.login())){
            log.error(String.format(USER_EXISTS, authRequest.login()));
            return "registration";
        }

        authService.register(authRequest);
        return "redirect:/login";
    }
}
