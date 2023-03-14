package com.shandrikov.bookshop.controllers.restcontrollers;

import com.shandrikov.bookshop.DTOs.CartItemDTO;
import com.shandrikov.bookshop.DTOs.OrderDTO;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.exceptions.BookNotFoundException;
import com.shandrikov.bookshop.exceptions.InvalidNumberBooksException;
import com.shandrikov.bookshop.services.ShoppingCartService;
import com.shandrikov.bookshop.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.shandrikov.bookshop.utils.StringPool.BOOK_NOT_FOUND;
import static com.shandrikov.bookshop.utils.StringPool.INVALID_QUANTITY_MORE_10;

@RestController
@RequestMapping("/restapi")
@RequiredArgsConstructor
public class RestShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public List<CartItemDTO> getAllCartItems (@AuthenticationPrincipal User user){
        return ObjectMapperUtils.mapAll(shoppingCartService.getAll(user), CartItemDTO.class);
    }

    @PostMapping("/cart/add/{bookId}/{quantity}")
    public int addBookToCart (@PathVariable("bookId") int bookId, @PathVariable("quantity") int quantity, @AuthenticationPrincipal User user){
        int updatedQuantity = 0;
        try {
            updatedQuantity = shoppingCartService.addBook(bookId, quantity, user);
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, BOOK_NOT_FOUND);
        } catch (InvalidNumberBooksException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_QUANTITY_MORE_10);
        }
        return updatedQuantity;
    }

    @DeleteMapping("/cart/remove/{bookId}")
    public void removeBookFromCart (@PathVariable("bookId") int bookId, @AuthenticationPrincipal User user){
        shoppingCartService.deleteItem(bookId, user);
    }

    @PostMapping("/cart/order")
    public OrderDTO createOrderFromCart (@AuthenticationPrincipal User user) {
        return shoppingCartService.createOrder(user);
    }
}
