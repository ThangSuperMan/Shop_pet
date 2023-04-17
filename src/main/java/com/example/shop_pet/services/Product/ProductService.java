package com.example.shop_pet.services.Product;

import com.example.shop_pet.models.Product;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductService {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<Product> selectProducts() {
    logger.info("BookService, selectProducts is running...");
    String sql =
        "SELECT *, to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') as created_at_formated FROM products ";
    List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper());
    return products;
  }

  public Optional<Product> selectProductById(Long id) {
    logger.info("BookService, selectProductById is running...");
    String sql =
        "SELECT *, to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') as created_at_formated FROM products WHERE id=?";
    Optional<Product> product =
        jdbcTemplate.query(sql, new ProductRowMapper(), id).stream().findFirst();
    return product;
  }
}