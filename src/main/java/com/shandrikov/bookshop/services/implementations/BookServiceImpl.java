package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.repositories.BookRepository;
import com.shandrikov.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
