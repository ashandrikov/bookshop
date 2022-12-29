package com.shandrikov.bookshop.repositories;

import com.shandrikov.bookshop.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByLoginIgnoreCase(String username);
}
