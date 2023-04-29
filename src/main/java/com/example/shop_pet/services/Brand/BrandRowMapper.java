package com.example.shop_pet.services.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.shop_pet.models.Brand;

public class BrandRowMapper implements RowMapper<Brand> {
  @Override
  @Nullable
  public Brand mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Brand(
      resultSet.getString("id"),
      resultSet.getString("name")
    );
  }
}
