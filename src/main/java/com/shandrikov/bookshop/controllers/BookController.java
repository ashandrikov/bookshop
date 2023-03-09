package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.Book;
import com.shandrikov.bookshop.exceptions.BookNotFoundException;
import com.shandrikov.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.shandrikov.bookshop.utils.StringPool.BOOK_ADDED;
import static com.shandrikov.bookshop.utils.StringPool.BOOK_DELETED;
import static com.shandrikov.bookshop.utils.StringPool.MESSAGE;
import static com.shandrikov.bookshop.utils.StringPool.ERROR;

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

    @GetMapping("/book/new")
    public String addBook(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes){
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute(MESSAGE, BOOK_ADDED);
        return "redirect:/api/books";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id,
                             RedirectAttributes redirectAttributes){
        try {
            bookService.deleteOne(id);
            redirectAttributes.addFlashAttribute(MESSAGE, BOOK_DELETED);
        } catch (BookNotFoundException | DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
        }
        return "redirect:/api/books";
    }
}
