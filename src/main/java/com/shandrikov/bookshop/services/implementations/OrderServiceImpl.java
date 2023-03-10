package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.DTOs.OrderDTO;
import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.OrderRepository;
import com.shandrikov.bookshop.services.OrderService;
import com.shandrikov.bookshop.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return ObjectMapperUtils.mapAll(orderRepository.findAll(), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrdersForUser(User user) {
        return ObjectMapperUtils.mapAll(orderRepository.findByUser(user), OrderDTO.class);
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
