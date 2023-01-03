package com.shandrikov.bookshop.services;

import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    void deleteAll();
    void deleteOrder(Order order);
    List<Order> getAllOrdersForUser(User user);
}
