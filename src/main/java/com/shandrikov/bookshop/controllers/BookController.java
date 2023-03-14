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

import static com.shandrikov.bookshop.utils.StringPool.BOOK_ADDED_EDITED;
import static com.shandrikov.bookshop.utils.StringPool.BOOK_DELETED;
import static com.shandrikov.bookshop.utils.StringPool.ERROR;
import static com.shandrikov.bookshop.utils.StringPool.MESSAGE;

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

    @PostMapping("/book/new")
    public String addBook(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("pageTitle", "Create new book");
        return "book-form";
    }

    @GetMapping("/book/edit/{id}")
    public String editBook(@PathVariable(name = "id") Long id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try {
            Book book = bookService.getOne(id);
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Edit book with Id: " + id);
            return "book-form";
        } catch (BookNotFoundException ex) {
            redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
            return "redirect:/api/books";
        }
    }

    @PostMapping("/book/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes){
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute(MESSAGE, BOOK_ADDED_EDITED);
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
