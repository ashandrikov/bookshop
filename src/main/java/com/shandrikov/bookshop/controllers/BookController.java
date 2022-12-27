package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restapi")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public List<Book> getAll(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public Book getOne(@PathVariable("id") Book book){
        return book;
    }

    @PostMapping("/book")
    public Book create(@Valid @RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/book/{id}")
    public Book update(@PathVariable("id") Book bookFromDB, @Valid @RequestBody Book book){
        BeanUtils.copyProperties(book, bookFromDB, "id");
        return bookService.saveBook(bookFromDB);
    }

    @DeleteMapping("/books")
    public void delete(){
        bookService.deleteAll();
    }
}
