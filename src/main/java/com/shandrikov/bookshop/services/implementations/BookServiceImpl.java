package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.exceptions.BookNotFoundException;
import com.shandrikov.bookshop.repositories.BookRepository;
import com.shandrikov.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shandrikov.bookshop.utils.StringPool.NO_BOOK_ID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public Book getOne(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(NO_BOOK_ID + id));
    }

    @Override
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(NO_BOOK_ID + id));
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
