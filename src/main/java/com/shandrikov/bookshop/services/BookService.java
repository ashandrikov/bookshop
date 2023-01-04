package com.shandrikov.bookshop.services;

import com.shandrikov.bookshop.domains.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    List<Book> getAllBooks();
    void deleteAll();
}
