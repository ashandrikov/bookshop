package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.exceptions.EmptyCartException;
import com.shandrikov.bookshop.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.shandrikov.bookshop.utils.StringPool.BOOK_ADDED_CART;
import static com.shandrikov.bookshop.utils.StringPool.ERROR;
import static com.shandrikov.bookshop.utils.StringPool.MESSAGE;
import static com.shandrikov.bookshop.utils.StringPool.ORDER_CREATED;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String getAllCartItems(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("shoppingCart", shoppingCartService.getAll(user));
        model.addAttribute("notEmpty", shoppingCartService.notEmpty(user));
        return "shopping-cart";
    }

    @GetMapping("/cart/add/{bookId}/{quantity}")
    public String addBookToCart(@PathVariable("bookId") int bookId,
                                @PathVariable("quantity") int quantity,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {
        try {
            shoppingCartService.addBook(bookId, quantity, user);
            redirectAttributes.addFlashAttribute(MESSAGE, BOOK_ADDED_CART);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
        }
        return "redirect:/api/books";
    }

    @GetMapping("/cart/order")
    public String createOrderFromCart(@AuthenticationPrincipal User user,
                                      RedirectAttributes redirectAttributes) {
        try {
            shoppingCartService.createOrder(user);
            redirectAttributes.addFlashAttribute(MESSAGE, ORDER_CREATED);
            return "redirect:/api/orders";
        } catch (EmptyCartException e) {
            redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
            return "redirect:/api/cart";
        }
    }
}
