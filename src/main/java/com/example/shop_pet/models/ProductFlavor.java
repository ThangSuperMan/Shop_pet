package com.example.shop_pet.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFlavor {
  private String productId;
  private String petFoodFlavorId;

  public ProductFlavor(String productId, String petFoodFlavorId) {
    this.productId = productId;
    this.petFoodFlavorId = petFoodFlavorId;
  }
}
