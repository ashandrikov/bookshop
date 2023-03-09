package com.shandrikov.bookshop.services;

import com.shandrikov.bookshop.domains.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    Book getOne(Long id);
    List<Book> getAllBooks();
    void deleteOne(Long id);
    void deleteAll();
}
