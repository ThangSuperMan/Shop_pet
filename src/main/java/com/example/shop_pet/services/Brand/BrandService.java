package com.example.shop_pet.services.Brand;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shop_pet.models.Brand;

@Repository
public class BrandService {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  public JdbcTemplate jdbcTemplate;

  public Optional<Brand> selectBrandById(Long brandId) {
    logger.info("BrandService selectBrandById is running...");
    String sql = "select * from brands where id = ?";
    Optional<Brand> brand = jdbcTemplate.query(sql, new BrandRowMapper(), brandId)
      .stream()
      .findFirst();
    return brand;
  }
}
