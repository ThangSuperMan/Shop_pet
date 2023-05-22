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
    Order order = new Order();
    order.setId(resultSet.getLong("id"));
    order.setUserId(resultSet.getString("user_id"));
    order.setFreeShipping(resultSet.getBoolean("is_free_shipping"));
    order.setPaymentStatus(resultSet.getString("payment_status"));
    order.setTotal(resultSet.getInt("total"));
    return order;
  }
}
