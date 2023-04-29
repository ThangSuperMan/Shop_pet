package com.example.shop_pet.services.FoodFlavor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shop_pet.models.FoodFlavor;
import com.example.shop_pet.models.ProductFlavor;

@Repository
public class FoodFlavorService {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<ProductFlavor> selectFlavorIdsByProductId(Long productId) {
    logger.info("FoodFlavorService selectFlavorsByProductId is running...");
    String sql = "SELECT * FROM product_flavors where product_id = ?";
    List<ProductFlavor> productFlavors = jdbcTemplate.query(sql, new ProductFlavorRowMapper(), productId);
    return productFlavors;
  }

  public List<FoodFlavor> selectFlavorsById(Long productId) {
    logger.info("FoodFlavorService selectFlavorsById is running...");
    // String sql = "SELECT * FROM pet_food_flavors where id = ?";
    String sql = """
                  select pet_food_flavors.id, pet_food_flavors.name 
                  from products a 
                  left join product_flavors b 
                  on a.id = b.product_id
                  left join pet_food_flavors
                  on pet_food_flavors.id = b.pet_food_flavor_id
                  where a.id = ?
                """;
    List<FoodFlavor> flavors = jdbcTemplate.query(sql, new PetFoodFlavorRowMapper(), productId);
    return flavors;
  }
}
