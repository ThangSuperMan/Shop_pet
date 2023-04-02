package com.example.shop_pet.services.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.example.shop_pet.models.User;
import com.example.shop_pet.services.Book.BookService;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(BookService.class);

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertUser(User user) {
        logger.info("insertUser service is running...");
        String sql = "insert into users (username, password, email) values (?, ?, ?)";
        int result = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
        return result;
    }
}
