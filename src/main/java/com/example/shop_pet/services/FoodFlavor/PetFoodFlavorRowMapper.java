package com.example.shop_pet.services.FoodFlavor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.FoodFlavor;

public class PetFoodFlavorRowMapper implements RowMapper<FoodFlavor> {
  @Override
  @Nullable
  public FoodFlavor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new FoodFlavor(
      resultSet.getString("id"), 
      resultSet.getString("name")
    );
  }
}
