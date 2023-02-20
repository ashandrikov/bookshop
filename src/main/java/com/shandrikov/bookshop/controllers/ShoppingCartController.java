package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String getAllCartItems (@AuthenticationPrincipal User user, Model model){
        model.addAttribute("shoppingCart", shoppingCartService.getAll(user));
        model.addAttribute("username", user.getUsername());
        return "shopping-cart";
    }
}
