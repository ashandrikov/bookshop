package com.shandrikov.bookshop.domains;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndBook(User user, Book book);
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.user.id = ?2 AND c.book.id = ?3")
    void updateQuantity(int quantity, long userId, long bookId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user.id = ?1 AND c.book.id = ?2")
    void deleteByUserAndBook(long userId, long bookId);
}