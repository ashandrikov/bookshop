package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public String getAll(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/book")
    public String addBook(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes){
        bookService.saveBook(book);

        //TODO:
        // Cancel button on the form
        // Change getMapping to /book/new
        // Arrange js jquery support
        // Make new book form bit down (add margin in a good way)

        redirectAttributes.addFlashAttribute("message", "The book was added successfully!");
        return "redirect:/api/books";
    }
}
