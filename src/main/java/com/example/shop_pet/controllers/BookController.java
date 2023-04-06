package com.example.shop_pet.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop_pet.models.Book;
import com.example.shop_pet.services.Book.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final BookService bookService;

    public BookController(BookService bookservice) {
        this.bookService = bookservice;
    }

    @GetMapping("/welcome")
    public String welcome() {
        logger.info("/books/welcome just triggerd!");
        return "Welcome this endpoints is not secure";
    }

    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Book> getAllTheBooks() {
        return bookService.selectBooks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Optional<Book> getBook(@PathVariable Long id) {
        return bookService.selectBookById(id);
    }
}
