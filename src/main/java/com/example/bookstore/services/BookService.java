package com.example.bookstore.services;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bookstore.models.Book;

//  Tell will spring book that this should be a bean
//  And any class inside the applicationspringboot can get that bean
@Repository
public class BookService {
    Logger logger = LoggerFactory.getLogger(BookService.class);

    private final JdbcTemplate jdbcTemplate;

    public BookService(JdbcTemplate jdbcTemplate) {
        logger.info("jdbcTemplate from bean :>> " + jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        // logger.info("jdbcTemplate :>> " + jdbcTemplate);
    }

    public int addBook(Book book) {
        logger.info("addBook service is running...");
        String sql = "insert into books (title, author, price) values (?, ?, ?)";
        // int result = jdbcTemplate.update(sql, "Head First Java", "Thang Jenny",
        // 10.55f);
        int result = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPrice());

        return result;
    }

    public void updateBook(Book book) {
        String sql = "update books set price=? where title=?";
        int result = jdbcTemplate.update(sql, book.getPrice(), book.getTitle());

        if (result > 0) {
            logger.info("Update book successfully");
        }
    }
}
