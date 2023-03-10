package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public String getOrdersForUser(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("orders", orderService.getAllOrdersForUser(user));
        return "orders";
    }

    @GetMapping("/all-orders")
    public String getAllOrders(Model model){
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

}
