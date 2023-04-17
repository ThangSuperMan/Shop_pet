package com.example.shop_pet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {
  @NotNull(message = "Username shouldn't be null!") private String username;
  @NotNull(message = "Password shouldn't be null!")
  @Size(min = 5,
      message = "Your password is too short, please make sure it store at least 5 characters!")
  @Size(max = 30,
      message = "Your password is too long, please make sure it store maximum at 30 characters!")
  private String password;
}
