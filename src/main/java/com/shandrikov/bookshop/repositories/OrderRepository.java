package com.shandrikov.bookshop.repositories;

import com.shandrikov.bookshop.domains.Order;
import com.shandrikov.bookshop.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long>{
    List<Order> findByUser(User user);
    void deleteByUser(User user);
}
