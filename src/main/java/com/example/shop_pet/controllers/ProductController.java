package com.example.shop_pet.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop_pet.models.Product;
import com.example.shop_pet.models.ProductDetail;
import com.example.shop_pet.models.ProductImage;
import com.example.shop_pet.services.Product.ProductService;
import com.example.shop_pet.services.ProductDetail.ProductDetailService;
import com.example.shop_pet.services.ProductImage.ProductImageService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ProductService productService;
  @Autowired private ProductDetailService productDetailService;
  @Autowired private ProductImageService productImageService;

  @GetMapping("/products")
  public List<Product> getAllProducts() {
    List<Product> products = productService.selectProducts();
    return products;
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<?> getProduct(@PathVariable Long id) {
  // public Optional<Product> getProduct(@PathVariable Long id) {
    logger.info("ProductController getProduct method is running...");
    Optional<Product> product = productService.selectProductById(id);
    Optional<ProductDetail> productDetail = productDetailService.selectProductDetailById(id);
    List<ProductImage>  productImages = productImageService.selectProductImages(id);
    System.out.println("product :>> " + product);
    System.out.println("productDetail :>> " + productDetail);
    System.out.println("productImages :>> " + productImages);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("product", product);
    map.put("productDetail", productDetail);
    map.put("productImages", productImages);
    return new ResponseEntity<>(map, HttpStatus.OK);
    // return product;
  }
}
