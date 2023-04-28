package com.example.shop_pet.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage {
  private String id;
  private String productId;
  private String url;
  private String altText; 

  public ProductImage(String id, String productId, String url, String altText ) {
    this.id = id;
    this.productId = productId;
    this.url = url;
    this.altText = altText;
  }
}

