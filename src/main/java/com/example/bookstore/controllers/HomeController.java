package com.example.bookstore.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookService bookService;

    public HomeController(BookService bookService) {
        logger.info("Bookservice bean from tomcat container :>> " + bookService);
        this.bookService = bookService;
    }

    @RequestMapping()
    public String home() {
        return "Home page";
    }

    @PostMapping("/add_book")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        int result = bookService.addBook(book);
        if (result > 0) {
            logger.info("Insert book successfully");
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PutMapping("/add_book")
    public void updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
    }

}
