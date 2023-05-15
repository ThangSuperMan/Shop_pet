package com.example.shop_pet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Payment {

  private String id;
  private String userid;
  private double total;
  private String currency;
  private String method;
  private String intent;
  private String description;

  public Payment(double total, String currency, String method, String intent, String description) {
    this.total = total;
    this.currency = currency;
    this.method = method;
    this.intent = intent;
    this.description = description;
  }
}
