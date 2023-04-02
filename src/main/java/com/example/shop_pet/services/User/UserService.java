package com.example.shop_pet.services.User;

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

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertUser(User user) {
        logger.info("insertUser service is running...");
        String sql = "insert into users (username, password, email) values (?, ?, ?)";
        Boolean isUsernameExist = isUsernameExist(user.getUsername());
        if (isUsernameExist) {
            logger.warn("Username exists, please choose another one!");
            return -1;
        } 

        logger.info("Username does not exist, you can sign up with this one!");
        int result = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
        logger.info("Result after insert :>> " + result);
        return result;
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

        System.out.println("User from db :>> " + user);
        System.out.println("user.isPresent :>> " + user.isPresent());
        if (user.isPresent()) {
            return true;
        }
        return false;
    }
}
