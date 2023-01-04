package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.DTOs.OrderDTO;
import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.OrderService;
import com.shandrikov.bookshop.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restapi")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderDTO> getAll(){
        return ObjectMapperUtils.mapAll(orderService.getAllOrders(), OrderDTO.class);
    }

    @GetMapping("/order/{id}")
    public OrderDTO getOne(@PathVariable("id") Order order){
        return ObjectMapperUtils.map(order, OrderDTO.class);
    }

    @GetMapping("/user/orders")
    public List<OrderDTO> getAllForUser(@AuthenticationPrincipal User user){
        return ObjectMapperUtils.mapAll(orderService.getAllOrdersForUser(user), OrderDTO.class);
    }

    @DeleteMapping("/order/{id}")
    public void delete(@PathVariable("id") Order order){
        orderService.deleteOrder(order);
    }

    @DeleteMapping("/orders")
    public void delete(){
        orderService.deleteAll();
    }
}
