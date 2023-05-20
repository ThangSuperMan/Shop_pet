package com.example.shop_pet.services.User;

import com.example.shop_pet.models.User;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  public int insertUser(User user) {
    logger.info("insertUser service is running...");
    String sql = """
                  INSERT INTO users (username, password, email)
                  VALUES (?, ?, ?)
                """;
    Boolean isUsernameExist = isUsernameExist(user.getUsername());
    if (Boolean.TRUE.equals(isUsernameExist)) {
      logger.error("Username exists, please choose another one!");
      return -1;
    }
    return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
  }
  
  public Optional<User> selectUserByUsername(String username) {
    logger.info("selectUserByUsername UserService is running...");
    String sql = "SELECT * FROM users WHERE username = ?";
    return jdbcTemplate.query(sql, new UserRowMapper(), username).stream().findFirst();
  }

  public Boolean isUsernameExist(String username) {
    logger.info("isUserExists UserService is running...");
    String sql = "SELECT * FROM users WHERE username = ?";
    Optional<User> user = jdbcTemplate.query(sql, new UserRowMapper(), username).stream().findFirst();

    return user.isPresent();
  }
}
