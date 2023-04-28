package com.example.shop_pet.services.ProductDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.ProductDetail;

public class ProductDetailRowMapper implements RowMapper<ProductDetail> {
  @Override
  @Nullable
  public ProductDetail mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new ProductDetail(
      resultSet.getString("id"),
      resultSet.getString("product_id"),
      resultSet.getString("description")
    );
  }
}
