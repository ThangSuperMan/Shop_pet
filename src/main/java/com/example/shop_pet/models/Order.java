package com.example.shop_pet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {

  private double price;
  private String currency;
  private String method;
  private String intent;
  private String description;

  public Order(double price, String currency, String method, String intent, String description) {
    this.price = price;
    this.currency = currency;
    this.method = method;
    this.intent = intent;
    this.description = description;
  }
}
