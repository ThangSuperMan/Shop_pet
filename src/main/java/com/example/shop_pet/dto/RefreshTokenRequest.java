package com.example.shop_pet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
  @NotNull(message = "Refresh token shouldn't be null")
  private String token;
}
