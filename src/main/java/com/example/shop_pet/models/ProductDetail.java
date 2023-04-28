package com.example.shop_pet.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetail {
  private String id;
  private String productId;
  private String description;

  public ProductDetail(String id, String productId, String description) {
    this.id = id;
    this.productId = productId;
    this.description = description;
  }

}
