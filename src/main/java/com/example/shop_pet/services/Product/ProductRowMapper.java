package com.example.shop_pet.services.Product;

import com.example.shop_pet.models.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

public class ProductRowMapper implements RowMapper<Product> {
  @Override
  @Nullable
  public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Product(
      resultSet.getString("id"), 
      resultSet.getString("brand_id"),
      resultSet.getString("title"),
      resultSet.getDouble("price"),
      resultSet.getString("image_url"),
      resultSet.getString("money_type"),
      resultSet.getString("created_at"),
      resultSet.getString("updated_at")
    );
  }
}
