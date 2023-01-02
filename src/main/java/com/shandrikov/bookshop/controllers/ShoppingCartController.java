package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.CartItem;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.ShoppingCartService;
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
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public List<CartItem> getAllCartItems (@AuthenticationPrincipal User user){
        return shoppingCartService.getAll(user);
    }

    @PostMapping("/cart/add/{bookId}/{quantity}")
    public int addBookToCart (@PathVariable("bookId") int bookId, @PathVariable("quantity") int quantity, @AuthenticationPrincipal User user){
        return shoppingCartService.addBook(bookId, quantity, user);
    }

    @DeleteMapping("/cart/remove/{bookId}")
    public void removeBookFromCart (@PathVariable("bookId") int bookId, @AuthenticationPrincipal User user){
        shoppingCartService.deleteItem(bookId, user);
    }
}