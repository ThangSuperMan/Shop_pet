package com.example.shop_pet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

  private String orderId;
  private String productId;
  private int quantity;

  public OrderItem(String orderId, String productId, int quantity) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
  }
}
