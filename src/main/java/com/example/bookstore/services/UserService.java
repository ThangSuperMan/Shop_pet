package com.example.bookstore.services;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookstore.models.UserEntity;

import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.slf4j.Logger;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(BookService.class);
    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // UserEntity user = new UserEntity();
    // try {
    // user = getUserByUsername(username);
    // } catch (SQLException sqlException) {
    // logger.error("loadUserByUsernamee error :>> ", sqlException);
    // }

    // return new User(user.getUsername(), user.getPassword(), null);
    // return new User("thangphan", "1", null);
    // }

    public List<User> getUsers() {
        logger.info("getUsers service is running...");
        String sql = "select * from users where username=?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        return users;
    }

    public UserEntity getUserByUsername(String username) throws SQLException {
        logger.info("getUserByUsername service is running...");
        String sql = "select * from users where username=? limit=1";
        List<UserEntity> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        System.out.println("Users from db :> " + users);
        return users.get(0);
    }
}
