package com.example.shop_pet.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AddressController {
  Logger logger = LoggerFactory.getLogger(UserController.class);

  // @PostMapping("/payment/add_address") 
  // public ResponseEntity<?> addPaymentAddress(@RequestBody Address address) {
  //   logger.info("AddressController addPaymentAddress method is running...");
  // }
}
