package com.example.shop_pet.controllers;

import com.example.shop_pet.models.Product;
import com.example.shop_pet.services.Product.ProductService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ProductService productService;

  @GetMapping("/products")
  public List<Product> getAllProducts() {
    List<Product> products = productService.selectProducts();
    return products;
  }

  @GetMapping("/products/{id}")
  public Optional<Product> getBook(@PathVariable Long id) {
    Optional<Product> product = productService.selectProductById(id);
    return product;
  }
}
