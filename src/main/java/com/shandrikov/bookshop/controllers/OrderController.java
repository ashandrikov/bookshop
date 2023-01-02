package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restapi")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAll(){
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{id}")
    public Order getOne(@PathVariable("id") Order order){
        return order;
    }

    @GetMapping("/user/orders")
    public List<Order> getAllForUser(@AuthenticationPrincipal User user){
        return orderService.getAllOrdersForUser(user);
    }

    @PostMapping("/user/order")
    public Order create(@AuthenticationPrincipal User user){
        return orderService.createOrderFromShoppingCart(user);
    }

//    @PutMapping("/book/{id}")
//    public Book update(@PathVariable("id") Book bookFromDB, @Valid @RequestBody Book book){
//        BeanUtils.copyProperties(book, bookFromDB, "id");
//        return orderService.saveBook(bookFromDB);
//    }

    @DeleteMapping("/orders")
    public void delete(){
        orderService.deleteAll();
    }
}
