package com.shandrikov.bookshop;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.domains.CartItem;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartItemRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CartItemRepository cartItemRepo;

    private User user;
    private Book book;
    private CartItem cartItem;

    @BeforeEach
    void initEntity(){
        user = getUser();
        book = getBook();
        entityManager.persist(user);
        entityManager.persist(book);
        cartItem = getCartItem(user, book, 1);
        cartItemRepo.save(cartItem);
    }

    @Test
    void testSaveItem() {
        assertThat(cartItem.getId()).isPositive();
    }

    @Test
    void testFindByUser() {
        List<CartItem> itemsByUser = cartItemRepo.findByUser(user);
        assertThat(itemsByUser).isNotEmpty();
    }

    @Test
    void testFindByUserAndBook() {
        Optional<CartItem> itemByBookAndUser = cartItemRepo.findByUserAndBook(user, book);
        assertTrue(itemByBookAndUser.isPresent());
    }

    @Test
    void testUpdateQuantity() {
        cartItemRepo.updateQuantity(2, user.getId(), book.getId());
        Optional<CartItem> itemByBookAndUser = cartItemRepo.findByUserAndBook(user, book);
        assertThat(itemByBookAndUser.get().getQuantity()).isEqualTo(2);
    }

    @Test
    void testDeleteByUserAndBook() {
        cartItemRepo.deleteByUserAndBook(user.getId(), book.getId());
        assertTrue(cartItemRepo.findByUserAndBook(user, book).isEmpty());
    }

    private User getUser(){
        User user = new User();
        user.setLogin("testuser");
        return user;
    }

    private Book getBook(){
        Book book = new Book();
        book.setAuthor("testAuthor");
        book.setTitle("testTitle");
        return book;
    }

    private CartItem getCartItem(User user, Book book, int quantity){
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

}
