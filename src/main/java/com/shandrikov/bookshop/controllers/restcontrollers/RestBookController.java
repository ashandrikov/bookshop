package com.shandrikov.bookshop.controllers.restcontrollers;

import com.shandrikov.bookshop.DTOs.BookDTO;
import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.services.BookService;
import com.shandrikov.bookshop.utils.ObjectMapperUtils;
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
public class RestBookController {
    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDTO> getAll(){
        return ObjectMapperUtils.mapAll(bookService.getAllBooks(), BookDTO.class);
    }

    @GetMapping("/book/{id}")
    public BookDTO getOne(@PathVariable("id") Book book){
        return ObjectMapperUtils.map(book, BookDTO.class);
    }

    @PostMapping("/book")
    public Book create(@Valid @RequestBody BookDTO book){
        return bookService.saveBook(ObjectMapperUtils.map(book, Book.class));
    }

    @PutMapping("/book/{id}")
    public Book update(@PathVariable("id") Book bookFromDB, @Valid @RequestBody BookDTO book){
        BeanUtils.copyProperties(book, bookFromDB, "id");
        return bookService.saveBook(ObjectMapperUtils.map(bookFromDB, Book.class));
    }

    @DeleteMapping("/books")
    public void delete(){
        bookService.deleteAll();
    }
}
