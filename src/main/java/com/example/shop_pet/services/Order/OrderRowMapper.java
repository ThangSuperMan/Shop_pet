package com.example.shop_pet.services.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.enums.OrderEnum;
import com.example.shop_pet.models.Order;

public class OrderRowMapper implements RowMapper<Order> {
  @Override
  @Nullable
  public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    // String paymentStatusString = resultSet.getString("payment_status");
    // OrderEnum paymentStatus = convertToOrderEnum(paymentStatusString);

    // New
    Order order = new Order();
    order.setId(resultSet.getString("id"));
    // order.setUserId(UUID.fromString(resultSet.getString("user_id")));
    order.setUserId(resultSet.getString("user_id"));
    order.setProductId(resultSet.getString("product_id"));
    order.setCreatedAt(resultSet.getString("created_at"));
    order.setFreeShipping(resultSet.getBoolean("is_free_shipping"));
    // order.setPaymentStatus(paymentStatus);
    order.setPaymentStatus(resultSet.getString("payment_status"));
    order.setQuantity(resultSet.getInt("quantity"));
    order.setTotal(resultSet.getInt("total"));

    return order;

    // Old
    // return new Order(
    //   resultSet.getString("id"), 
    //   resultSet.getString("user_id"),
    //   resultSet.getString("product_id"),
    //   resultSet.getString("created_at"),
    //   resultSet.getBoolean("is_free_shipping"),
    //   resultSet.getString("payment_status"),
    //   resultSet.getInt("quantity"),
    //   resultSet.getInt("total")
    // );
  }

  private OrderEnum convertToOrderEnum(String paymentStatusString) {
    OrderEnum paymentStatus;

    switch (paymentStatusString) {
        case "unpaid":
            paymentStatus = OrderEnum.unpaid;
            break;
        case "paid":
            paymentStatus = OrderEnum.paid;
            break;
        case "pending":
            paymentStatus = OrderEnum.pending;
            break;
        case "refunded":
            paymentStatus = OrderEnum.refuned;
            break;
        default:
            // Handle unrecognized payment status values
            throw new IllegalArgumentException("Invalid payment status: " + paymentStatusString);
    }

    return paymentStatus;
}
}
