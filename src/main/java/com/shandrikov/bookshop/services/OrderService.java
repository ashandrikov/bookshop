package com.shandrikov.bookshop.services;

import com.shandrikov.bookshop.DTOs.OrderDTO;
import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    void deleteAll();
    void deleteOrder(Order order);
    List<OrderDTO> getAllOrdersForUser(User user);
}
