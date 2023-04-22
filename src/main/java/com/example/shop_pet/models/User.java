package com.example.shop_pet.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
  private String id;

  @NotNull(message = "Username shouldn't be null")
  private String username;

  @NotNull(message = "password shouldn't be null")

  @Size(min = 6, message = "Your password is too short, please make sure it store at least 5 characters!")
  @Size(max = 30, message = "Your password is too long, please make sure it store maximum at 30 characters!")
  private String password;

  @NotNull(message = "confirmPassword shouldn't be null")
  private String confirmPassword;

  private String email;
  private String avatarUrl;
  private String role;

  public User(String id, String username, String password, String email, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
  }
}
