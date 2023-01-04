package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.OrderRepository;
import com.shandrikov.bookshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersForUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public void deleteOrder(Order order){
        orderRepository.deleteById(order.getId());
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
