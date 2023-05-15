package com.example.shop_pet.services.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.Order;

public class OrderRowMapper implements RowMapper<Order> {
  @Override
  @Nullable
  public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Order(
      resultSet.getString("id"), 
      resultSet.getString("user_id"),
      resultSet.getString("created_at"),
      resultSet.getBoolean("is_free_shipping"),
      resultSet.getString("payment_status"),
      resultSet.getInt("total")
    );
  }
}
