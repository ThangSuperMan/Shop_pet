package com.example.shop_pet.services.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
  @Override
  @Nullable
  public OrderItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new OrderItem(
      resultSet.getLong("order_id"), 
      resultSet.getLong("product_id"),
      resultSet.getInt("quantity")
    );
  }
}
