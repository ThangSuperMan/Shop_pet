package com.example.shop_pet.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop_pet.models.Brand;
import com.example.shop_pet.models.FoodFlavor;
import com.example.shop_pet.models.Product;
import com.example.shop_pet.models.ProductDetail;
import com.example.shop_pet.models.ProductImage;
import com.example.shop_pet.services.Brand.BrandService;
import com.example.shop_pet.services.FoodFlavor.FoodFlavorService;
import com.example.shop_pet.services.Product.ProductService;
import com.example.shop_pet.services.ProductDetail.ProductDetailService;
import com.example.shop_pet.services.ProductImage.ProductImageService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
  Logger logger = LoggerFactory.getLogger(getClass());
  Integer itemsPerPage = 12;
  Integer pageSize = 12;

  @Autowired
  private ProductService productService;
  @Autowired
  private ProductDetailService productDetailService;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private BrandService brandService;
  @Autowired
  private FoodFlavorService foodFlavorService;

  @GetMapping("/products")
  public ResponseEntity<?> getAllProducts(
    @RequestParam(defaultValue = "1") Integer pageNumber 
  ) {
    logger.info("ProductController getAllProducts() is running...");
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Integer totalPages = productService.totalProducts() / itemsPerPage;
    System.out.println("totalPage :>> " + totalPages);
    System.out.println("pageNumber :>> " + pageNumber);
    System.out.println("pageSize :>> " + pageSize);
    Integer offset = pageable.getPageSize() * (pageable.getPageNumber() - 1); 
    List<Product> products = productService.selectProducts(pageable, offset);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("products", products);
    if (totalPages == 0) {
      return new ResponseEntity<>(map, HttpStatus.OK);
    }
    map.put("totalPages", totalPages);

    if (pageNumber > totalPages) {
      System.out.println("pageNumber > totalPages"); 
      String errorMessage = "The page you chosed was run out of the available page, please choose another one!";
      map.put("errorMessage", errorMessage);
      return new ResponseEntity<>(map, HttpStatus.OK);
    }
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  // @GetMapping("/products")
  // public List<Product> getAllProducts() {
  //   List<Product> products = productService.selectProducts();
  //   return products;
  // }

  public boolean isFoodProduct(Long productId) {
    int count = foodFlavorService.countNumberOfFlavors(productId);
    return count > 0 ? true : false;
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<?> getProduct(@PathVariable Long id) {
    logger.info("ProductController getProduct method is running...");
    Optional<Product> product = productService.selectProductById(id);
    Optional<ProductDetail> productDetail = productDetailService.selectProductDetailById(id);
    List<ProductImage> productImages = productImageService.selectProductImages(id);
    HashMap<String, Object> map = new HashMap<String, Object>();

    if (product.isPresent()) {
      Product prod = product.get();
      Long brandId = Long.parseLong(prod.getBrandId());
      Optional<Brand> brand = brandService.selectBrandById(brandId);
      List<FoodFlavor> flavorNames = foodFlavorService.selectFlavorsById(Long.parseLong(prod.getId()));

      if (isFoodProduct(Long.parseLong(prod.getId()))) {
        logger.info("isFoodProduct" + isFoodProduct(Long.parseLong(prod.getId())));
        map.put("foodFlavors", flavorNames);
      }

      map.put("brand", brand);
    }
    map.put("product", product);
    map.put("productDetail", productDetail);
    map.put("productImages", productImages);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
