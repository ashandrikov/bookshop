package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.OrderRepository;
import com.shandrikov.bookshop.repositories.UserRepository;
import com.shandrikov.bookshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    @Override
    public Order createOrderFromShoppingCart(User user) {
//        user.getBasket().getBooks... Add books from basket to order

        Order order = new Order();
        user.getOrders().add(order);
        userRepo.save(user);
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public List<Order> getAllOrdersForUser(User user) {
        return user.getOrders();
    }

    @Override
    public void deleteAll() {
        orderRepo.deleteAll();
    }
}
