package com.example.shop_pet.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
  // private Long id;
  // private UUID id;
  private String id;
  private String username;
  private String password;
  private String confirmPassword;
  private String email;
  private String role;

  public User(String id, String username, String password, String email, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
  }
}
