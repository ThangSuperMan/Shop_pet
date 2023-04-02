package com.example.shop_pet.services.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.Book;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    @Nullable
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Book(
            resultSet.getInt("id"),
            resultSet.getString("title"),
            resultSet.getString("author"),
            resultSet.getFloat("price"),
            resultSet.getString("created_at")
        );
    }
}