package com.shandrikov.bookshop.repositories;

import com.shandrikov.bookshop.domains.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
}
