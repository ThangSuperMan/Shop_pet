package com.example.shop_pet.services.ProductImage;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.ProductImage;

public class ProductImageRowMapper implements RowMapper<ProductImage> {
  @Override
  @Nullable
  public ProductImage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new ProductImage(
      resultSet.getString("id"),
      resultSet.getString("product_id"),
      resultSet.getString("url"),
      resultSet.getString("alt_text")
    );
  }
}
