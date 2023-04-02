package com.example.bookstore.services.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.bookstore.models.Book;

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