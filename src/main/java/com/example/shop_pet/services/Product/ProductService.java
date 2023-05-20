package com.example.shop_pet.services.Product;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shop_pet.models.Product;

@Repository
public class ProductService {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Integer totalProducts() {
    logger.info("ProductService totalProducts is running");
    String sql = """ 
                SELECT COUNT(*)
                FROM products
                """;
    int total = jdbcTemplate.queryForObject(sql, Integer.class);
    return total;
  }

  public void regenerateView(Pageable pageable, Integer offset) {
    String sqlCreateView = "CREATE OR REPLACE VIEW products_sorted_limit_offset_view AS " +
        "SELECT * FROM get_products_sorted_limit_offset($1, $2)";

    jdbcTemplate.update(sqlCreateView, pageable.getPageSize(), offset);
  }

  public List<Product> selectProducts(Pageable pageable, Integer offset) {
    // Views with functions
    logger.info("ProductService selectProducts is running...");
    String pageSql = """
                    SELECT * 
                    FROM products 
                    ORDER BY id
                    LIMIT ? OFFSET ?
                    """;
    // String pageSql = """
    //                  CREATE OR REPLACE VIEW products_sorted_limit_offset_view AS
    //                  SELECT * 
    //                  FROM get_products_sorted_limit_offset($1, $2)
    //                 """;

    // String sqlCreateView = "CREATE OR REPLACE VIEW products_sorted_limit_offset_view AS " +
    //          "SELECT * FROM get_products_sorted_limit_offset($1, $2)";
    // CREATE OR REPLACE VIEW products_sorted_limit_offset_view AS SELECT * FROM get_products_sorted_limit_offset(10, 0);

    // regenerateView(pageable, offset);
    // String sqlGetDataFromView = "select * from products_sorted_limit_offset_view";

    // int limitValue = 10;
    // int offsetValue = 0;

    // jdbcTemplate.execute(sqlCreateView, (PreparedStatementCallback<Object>) ps -> {
    //     ps.setInt(1, pageable.getPageSize());
    //     ps.setInt(2, offset);
    //     return ps.execute();
    // });

    List<Product> products = jdbcTemplate.query(pageSql, new ProductRowMapper(), new Object[] {pageable.getPageSize(), offset});
    return products;
  }

  // public List<Product> selectProducts() {
  //   logger.info("ProductService selectProducts is running...");
  //   // String sql = "SELECT *, to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') as
  //   // created_at_formated FROM products ";
  //   String sql = "SELECT * FROM products";
  //   List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper());
  //   return products;
  // }

  public Optional<Product> selectProductByTitle(String title) {
    logger.info("BookService, selectProductById is running...");
    String sql = """
              SELECT *, to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') AS created_at_formated
              FROM products 
              WHERE title = ?
              """;
    Optional<Product> product = jdbcTemplate.query(sql, new ProductRowMapper(), title)
                                .stream()
                                .findFirst();
    return product;
  }

  public Optional<Product> selectProductById(Long id) {
    logger.info("BookService, selectProductById is running...");
    String sql = """
              SELECT *, to_char(created_at, 'YYYY/MM/dd HH24:MI:SS') AS created_at_formated
              FROM products 
              WHERE id = ?
              """;
    Optional<Product> product = jdbcTemplate.query(sql, new ProductRowMapper(), id).stream().findFirst();
    return product;
  }

  public Optional<Product> selectProductDescription(Long id) {
    logger.info("BookService, selectProductById is running...");
    String sql = """ 
              SELECT * 
              FROM product_detaiil 
              WHERE product_id = ? 
              """;
    Optional<Product> product = jdbcTemplate.query(sql, new ProductRowMapper(), id).stream().findFirst();
    return product;
  }
}
