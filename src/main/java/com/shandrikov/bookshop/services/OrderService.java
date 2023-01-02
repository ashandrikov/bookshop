package com.shandrikov.bookshop.services;

import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface OrderService {
    Order createOrderFromShoppingCart(User user);

    List<Order> getAllOrders();

    void deleteAll();

    List<Order> getAllOrdersForUser(User user);
}
