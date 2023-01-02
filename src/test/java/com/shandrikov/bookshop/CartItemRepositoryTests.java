package com.shandrikov.bookshop;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.domains.CartItem;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
class CartItemRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CartItemRepository cartItemRepo;

    @Test
    void testSaveItem() {
        int userId = 2;
        int bookId = 1;

        User user = entityManager.find(User.class, userId);
        Book book = entityManager.find(Book.class, bookId);

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(1);
        CartItem savedItem = cartItemRepo.save(cartItem);

        assertThat(savedItem.getId()).isPositive();
    }

    @Test
    void testFindByUser() {
        int userId = 2;

        User user = entityManager.find(User.class, userId);

        List<CartItem> itemsByUser = cartItemRepo.findByUser(user);

        assertThat(itemsByUser).isNotEmpty();
    }

    @Test
    void testFindByUserAndBook() {
        int userId = 2;
        int bookId = 1;

        User user = entityManager.find(User.class, userId);
        Book book = entityManager.find(Book.class, bookId);

        Optional<CartItem> itemByBookAndUser = cartItemRepo.findByUserAndBook(user, book);

        assertTrue(itemByBookAndUser.isPresent());
        System.out.println("itemByBookAndUser = " + itemByBookAndUser);
    }

    @Test
    @Transactional
    void testUpdateQuantity() {
        int userId = 2;
        int bookId = 1;

        cartItemRepo.updateQuantity(2, userId, bookId);

        User user = entityManager.find(User.class, userId);
        Book book = entityManager.find(Book.class, bookId);

        Optional<CartItem> itemByBookAndUser = cartItemRepo.findByUserAndBook(user, book);

        assertThat(itemByBookAndUser.get().getQuantity()).isEqualTo(2);
    }

    @Test
    void testDeleteByUserAndBook() {
        int userId = 2;
        int bookId = 1;

        User user = entityManager.find(User.class, userId);
        Book book = entityManager.find(Book.class, bookId);

        cartItemRepo.deleteByUserAndBook(userId, bookId);

        Optional<CartItem> itemByBookAndUser = cartItemRepo.findByUserAndBook(user, book);

        assertTrue(itemByBookAndUser.isEmpty());
    }

}
