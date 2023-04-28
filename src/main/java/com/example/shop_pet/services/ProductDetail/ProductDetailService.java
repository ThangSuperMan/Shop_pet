package com.example.shop_pet.services.ProductDetail;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shop_pet.models.ProductDetail;

@Repository
public class ProductDetailService {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Optional<ProductDetail> selectProductDetailById(Long id) {
    logger.info("BookService, selectProductDetailById is running...");
    String sql = "select * from product_detail where product_id = ?";
    Optional<ProductDetail> productDetail = jdbcTemplate.query(sql, new ProductDetailRowMapper(), id).stream().findFirst();
    return productDetail;
  }
}

