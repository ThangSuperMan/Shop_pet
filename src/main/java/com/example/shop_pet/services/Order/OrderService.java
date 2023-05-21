package com.example.shop_pet.services.Order;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.shop_pet.models.Order;
import com.example.shop_pet.models.OrderItem;
import com.example.shop_pet.services.User.UserService;

@Service
public class OrderService {
  Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<OrderItem> selectOrderItemsByOrderId(Integer orderId) {
    String sql = """
              SELECT * 
              FROM order_items 
              WHERE order_id = ? 
              """;
    List<OrderItem> orderItems = jdbcTemplate.query(sql, new OrderItemRowMapper(), orderId);
    return orderItems;
  }

  public Optional<Order> selectOrderUnpaidByUserId(String userId) {
    logger.info("OrderService ce is running...");
    String sql = """
              SELECT * 
              FROM orders 
              WHERE user_id = CAST(? AS UUID) 
              """;
    Optional<Order> order = jdbcTemplate.query(sql, new OrderRowMapper(), userId)
                            .stream()
                            .findFirst();
    return order;
  }

  public int countNumberOfOrderItemByOrderIdAndProductId(String orderId, String productId) {
    logger.info("FoodFlavorService countNumberOfOrderItemByOrderIdAndProductId is running...");
    String sql = """
                  SELECT COUNT(*) 
                  FROM order_items 
                  WHERE order_id = CAST(? AS INTEGER)
                  AND product_id = CAST(? AS INTEGER)
                  """;
    return jdbcTemplate.queryForObject(sql, Integer.class, orderId, productId);
  }

  public int countNumberOfOrderUnpaidByUserId(String userId) {
    logger.info("FoodFlavorService selectNumberOfFlavors is running...");
    String sql = """
                  SELECT COUNT(*) 
                  FROM orders 
                  WHERE user_id = CAST(? AS UUID)
                  AND payment_status = 'unpaid'
                  """;
    return jdbcTemplate.queryForObject(sql, Integer.class, userId);
  }

  public int insertOrderItems(OrderItem orderItem) {
    logger.info("OrderService insertOrderItems method is running...");
    String sql = """
              INSERT INTO order_items (order_id, product_id, quantity)
              VALUES (CAST(? AS INTEGER), CAST(? AS INTEGER) , ?) 
              """;
    return jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity());
  }
  
  public int insertOrder(Order order) {
    logger.info("OrderService insertOrder method is running...");
    String sql = """
              INSERT INTO orders (user_id, total)
              VALUES (CAST(? AS UUID), ?) 
              """;
    return jdbcTemplate.update(sql, order.getUserId(), order.getTotal());
  }

  public void updateOrderItemQuantityByOrderId(Integer quantity, String orderId) {
    logger.info("OrderService updateOrderItemQuantityByOrderId method is running...");
    String sql = """
              UPDATE order_items 
              SET quantity = ? 
              WHERE order_id = ?
              """;
    jdbcTemplate.update(sql, quantity, orderId);
  }
}
