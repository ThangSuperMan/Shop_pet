package com.example.shop_pet.services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.example.shop_pet.models.User;
import com.example.shop_pet.services.Book.BookService;

import java.util.Optional;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertUser(User user) {
        logger.info("insertUser service is running...");
        String sql = "insert into users (username, password, email) values (?, ?, ?)";
        Boolean isUsernameExist = isUsernameExist(user.getUsername());
        if (Boolean.TRUE.equals(isUsernameExist)) {
            logger.error("Username exists, please choose another one!");
            return -1;
        }
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }

    public Optional<User> selectUserByUsername(String username) {
        logger.info("selectUserByUsername UserService is running...");
        String sql = """
                SELECT *
                FROM users
                where username=?
                """;
        return jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream()
                .findFirst();
    }

    public Boolean isUsernameExist(String username) {
        logger.info("isUserExists UserService is running...");
        String sql = """
                    select *
                    from users
                    where username=?
                """;
        Optional<User> user = jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream()
                .findFirst();

        return user.isPresent();
    }
}
