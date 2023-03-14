package com.shandrikov.bookshop.services;

import com.shandrikov.bookshop.DTOs.OrderDTO;
import com.shandrikov.bookshop.domains.CartItem;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface ShoppingCartService {
    int addBook(long bookId, int quantity, User user);
    boolean notEmpty(User user);
    void deleteItem(long bookId, User user);
    List<CartItem> getAll(User user);
    OrderDTO createOrder(User user);
}
