// package com.example.shop_pet.services.Book;

// import com.example.shop_pet.models.Book;
// import java.util.List;
// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Repository;

// @Repository
// public class BookService {
// Logger logger = LoggerFactory.getLogger(BookService.class);
// private final JdbcTemplate jdbcTemplate;

// public BookService(JdbcTemplate jdbcTemplate) {
// this.jdbcTemplate = jdbcTemplate;
// }

// public List<Book> selectBooks() {
// logger.info("selectBooks service is running...");
// String sql = " SELECT * FROM books ";
// List<Book> books = jdbcTemplate.query(sql, new BookRowMapper());
// return books;
// }

// public Optional<Book> selectBookById(Long id) {
// logger.info("selectBookById service is running...");
// String sql = " SELECT* FROM books where id = ?";
// Optional<Book> book = jdbcTemplate.query(sql, new BookRowMapper(),
// id).stream().findFirst();
// return book;
// }

// public int deleteBook(int id) {
// logger.info("deleteBook service is running...");
// String sql = "delete from books where id = ? ";
// return jdbcTemplate.update(sql, id);
// }

// public int insertBook(Book book) {
// logger.info("insertBook service is running...");
// String sql = "insert into books (title, author, price) values (?, ?, ?)";
// int result = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(),
// book.getPrice());
// return result;
// }

// public int updateBookById(Book book, Long id) {
// logger.info("updateBook service is running...");
// String sql = "update books set price=? where id=?";
// int result = jdbcTemplate.update(sql, book.getPrice(), id);
// return result;
// }
// }
