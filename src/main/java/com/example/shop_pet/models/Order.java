package com.example.shop_pet.models;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
  private String id;
  @NotNull(message = "userid shouldn't be null") 
  private UUID userId;

  @NotNull(message = "productId shouldn't be null") 
  private String productId;

  private String createdAt;
  private boolean isFreeShipping;
  private String paymentStatus;
  
  @Min(1)
  @Max(9)
  private int quantity;

  private int total;

  public Order(String id, UUID userId, String productId, String createdAt, boolean isFreeShipping, String paymentStatus, int quantity, int total) {
    this.id = id;
    this.userId = userId;
    this.productId = productId;
    this.createdAt = createdAt;
    this.isFreeShipping = isFreeShipping; 
    this.paymentStatus = paymentStatus;
    this.quantity = quantity;
    this.total = total;
  }
}
