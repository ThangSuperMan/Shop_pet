package com.example.shop_pet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthOrderItem {
  @NotNull(message = "orderId shouldn't be null")
  private Long orderId;

  @NotNull(message = "productId shouldn't be null")
  private Long productId;

  public AuthOrderItem(Long orderId, Long productId) {
    this.orderId = orderId;
    this.productId = productId;
  }
}
