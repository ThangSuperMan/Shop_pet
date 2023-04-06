package com.example.shop_pet.controllers;

import java.util.List;
import java.util.Optional;

import com.example.shop_pet.models.Book;
import com.example.shop_pet.services.Book.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {
    Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookService bookService;

    @RequestMapping("/greedy")
    public String hello() {
        return "Home page";
    }

    @RequestMapping("/books/{id}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable Long id) {
        logger.info("getBook controller is running...");
        Optional<Book> book = bookService.selectBookById(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        logger.info("getAllBooks controller is running...");
        List<Book> books = bookService.selectBooks();
        if (books.size() > 0) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        return new ResponseEntity<>(books, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add_book")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        int result = bookService.insertBook(book);
        if (result > 0) {
            logger.info("Insert book successfully");
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/book_update/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable Long id) {
        logger.info("updateBook controller is running...");
        int result = bookService.updateBookById(book, id);
        System.out.println("result after update book :>> " + result);
        if (result > 0) {
            logger.info("Update book successfully!");
            return new ResponseEntity<>(book, HttpStatus.OK);
            // System.out.println("result after update book :>> " + result);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
