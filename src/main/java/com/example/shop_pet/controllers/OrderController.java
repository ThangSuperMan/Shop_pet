package com.example.shop_pet.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop_pet.models.Order;
import com.example.shop_pet.models.OrderItem;
import com.example.shop_pet.services.Order.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
  Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired OrderService orderService;


  @PostMapping("/orders")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<?>getOrder (@RequestBody String userId) {
    logger.info("OrderController getOrder method is running...");
    HashMap<String, Object> orderItems = new HashMap();
    return new ResponseEntity<>("Ok", HttpStatus.OK);
  }

  @PostMapping("/orders/save")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<?> saveOrder(@RequestBody @Valid Order order) {
    logger.info("OrderController saveOrder method is running...");
    HashMap<String, Object> map = new HashMap<String, Object>();

    logger.info("Product info :>> " + order.toString());

    OrderItem orderItems = new OrderItem(order.getId(), order.getProductId(), order.getQuantity());

    int resultInsertOrder = orderService.insertOrder(order);
    // int resultInsertOrderItems = orderService.insertOrderItems(orderItems);

    System.out.println("resultInsertOrder :>> " + resultInsertOrder);
    if (resultInsertOrder > 0) {
      logger.info("Insert Order successfully");
      String message = "Insert Order successfully";
      map.put("messageOne", message);
    }
    // if (resultInsertOrderItems > 0) {
    //   logger.info("Insert OrderItems successfully");
    //   String message = "Insert OrderItems successfully";
    //   map.put("messageTwo", message);
    // }

    // map.put("message", "nothing");
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
