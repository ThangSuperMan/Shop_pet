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

  public void addSeoProductTiles(List<Product> products) {
    // Modified the product seo title for product detail page use
    if (!products.isEmpty()) {
      for (Product product : products) {
        String seoTitle = product.getTitle().replace(" ", "-");
        product.setSeoTitle(seoTitle);
      }
    }
  }

  @GetMapping("/products")
  public ResponseEntity<?> getAllProducts(
      @RequestParam(defaultValue = "1") Integer pageNumber) {
    logger.info("ProductController getAllProducts() is running...");
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Integer totalPages = productService.totalProducts() / itemsPerPage;

    System.out.println("totalPage :>> " + totalPages);
    System.out.println("pageNumber :>> " + pageNumber);
    System.out.println("pageSize :>> " + pageSize);

    Integer offset = pageable.getPageSize() * (pageable.getPageNumber() - 1);
    List<Product> products = productService.selectProducts(pageable, offset);
    // addSeoProductTiles(products);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("products", products);
    if (totalPages == 0) {
      return new ResponseEntity<>(map, HttpStatus.OK);
    }
    map.put("totalPages", totalPages);

    if (pageNumber > totalPages) {
      String errorMessage = "The page you chose was run out of the available page, please choose another one!";
      map.put("errorMessage", errorMessage);
      return new ResponseEntity<>(map, HttpStatus.OK);
    }
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  public boolean isFoodProduct(Long productId) {
    int count = foodFlavorService.countNumberOfFlavors(productId);
    return count > 0 ? true : false;
  }

  @GetMapping("/products/{productTitle}")
  public ResponseEntity<?> getProduct(@PathVariable String productTitle) {
    logger.info("ProductController getProduct method is running...");
    // Convert from product-title -> product title
    String originalProductTitle = productTitle.replace("-", " ");
    System.out.println("productTitle :>> " + productTitle);

    Optional<Product> product = productService.selectProductByTitle(originalProductTitle);
    logger.info("product :>> " + product);
    
    HashMap<String, Object> map = new HashMap<String, Object>();
    product.ifPresent(obj -> {
      Long productId = Long.parseLong(obj.getId());
      Optional<ProductDetail> productDetail = productDetailService.selectProductDetailById(productId);
      List<ProductImage> productImages = productImageService.selectProductImages(productId);
      map.put("productDetail", productDetail);
      map.put("productImages", productImages);
    });

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
    // map.put("productDetail", productDetail);
    // map.put("productImages", productImages);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
