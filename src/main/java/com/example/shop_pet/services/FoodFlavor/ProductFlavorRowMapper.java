package com.example.shop_pet.services.FoodFlavor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.ProductFlavor;

public class ProductFlavorRowMapper implements RowMapper<ProductFlavor> {
  @Override
  @Nullable
  public ProductFlavor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new ProductFlavor(
      resultSet.getString("product_id"), 
      resultSet.getString("pet_food_flavor_id")
    );
  }
}
