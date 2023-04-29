package com.example.shop_pet.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Brand {
  private String id;
  private String name;

  public Brand(String id, String name)  {
    this.id = id; 
    this.name = name; 
  }
}
