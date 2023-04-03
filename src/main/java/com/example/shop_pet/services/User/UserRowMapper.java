package com.example.shop_pet.services.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.shop_pet.models.User;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

public class UserRowMapper implements RowMapper<User> {
    @Override
    @Nullable
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("email"),
            resultSet.getString("role")
        );
    }
}