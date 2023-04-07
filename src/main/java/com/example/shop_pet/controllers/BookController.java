package com.example.shop_pet.controllers;

import java.util.List;
import java.util.Optional;

import com.example.shop_pet.dto.AuthRequest;
import com.example.shop_pet.models.Book;
import com.example.shop_pet.services.Book.BookService;
import com.example.shop_pet.utils.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final BookService bookService;

		@Autowired
		private JwtUtils jwtUtils;

		@Autowired
		private AuthenticationManager authenticationManager;

    public BookController(BookService bookservice) {
        this.bookService = bookservice;
    }

    @GetMapping("/welcome")
    public String welcome() {
        logger.info("/books/welcome just triggerd!");
        return "Welcome this endpoints is not secure";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Book> getAllTheBooks() {
        return bookService.selectBooks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Optional<Book> getBook(@PathVariable Long id) {
        return bookService.selectBookById(id);
    }

		@PostMapping("/authenticate")
		public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
				logger.info("/books/authenticate just triggered!");
				logger.info("authRequest :>>" + authRequest);
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
				if (authentication.isAuthenticated()) {
						logger.info("autheries :>> " + authentication.getAuthorities());
						return jwtUtils.generateToken(authRequest.getUsername());
				} else {
						throw new UsernameNotFoundException("invalid user request!");
				}
		}
}

