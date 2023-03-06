package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
}
