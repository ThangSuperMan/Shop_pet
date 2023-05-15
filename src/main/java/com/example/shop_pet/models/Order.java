package com.example.shop_pet.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
  private String id;
  private String userId;
  private String createdAt;
  private boolean isFreeShipping;
  private String paymentStatus;
  private int total;

  public Order(String id, String userId, String createdAt, boolean isFreeShipping, String paymentStatus, int total) {
    this.id = id;
    this.userId = userId;
    this.createdAt = createdAt;
    this.isFreeShipping = isFreeShipping; 
    this.paymentStatus = paymentStatus;
    this.total = total;
  }
}
