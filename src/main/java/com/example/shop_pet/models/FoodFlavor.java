package com.example.shop_pet.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodFlavor {
  private String id;
  private String name;

  public FoodFlavor (String id, String name) {
    this.id = id;
    this.name = name;
  }
}
