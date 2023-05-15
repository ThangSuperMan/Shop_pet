package com.example.shop_pet.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop_pet.models.Order;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
  Logger logger = LoggerFactory.getLogger(UserController.class);

  // @PostMapping("/orders/save")
  // public ResponseEntity<?> saveOrder(@RequestBody Order order) {
  //   logger.info("OrderController saveOrder method is running...");
  //   HashMap<String, Object> map = new HashMap<>();
  // }
}
