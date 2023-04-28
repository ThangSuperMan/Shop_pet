package com.example.shop_pet.services.ProductImage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shop_pet.models.ProductImage;

@Repository
public class ProductImageService {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<ProductImage> selectProductImages(Long productId)  {
    logger.info("ProductImageService selectProducts method is running...");
    String sql = "select * from product_images where product_id = ?";
    List<ProductImage> productImages = jdbcTemplate.query(sql, new ProductImageRowMapper(), productId); 
    System.out.println("productImages form the db :>> " + productImages);
    return productImages;
  }
}
