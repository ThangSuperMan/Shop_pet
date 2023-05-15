package com.example.shop_pet.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop_pet.services.Paypal.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
  Logger logger = LoggerFactory.getLogger(getClass());

  public static final String BASE_URL = "http://localhost:3001/api/v1/";
  public static final String SUCCESS_URL = "pay/success";
  public static final String CANCEL_URL = "pay/cancel";

  @Autowired
  PaypalService paypalService;

  @PostMapping("/payment")
  public ResponseEntity<?> payment(@RequestBody com.example.shop_pet.models.Payment order) throws PayPalRESTException {
    logger.info("PaymentController payment method is running");
    Payment payment = paypalService.createPayment(order.getTotal(), order.getCurrency(), order.getMethod(), order.getIntent(), order.getDescription(), BASE_URL + CANCEL_URL, BASE_URL + SUCCESS_URL);
    HashMap<String, String> map = new HashMap<>();

    for (Links link : payment.getLinks()) {
      if (link.getRel().equals("approval_url")) {
        logger.info("link :>> ", link);
        map.put("redirectUrl", link.getHref());
        return new ResponseEntity<>(map, HttpStatus.OK);
        // return "redirect:" + link.getHref();
      }
    }

    // return "redirect:/";
    map.put("redirectUr", "/");
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping(CANCEL_URL)
  public String cancelPay() {
      return "cancel";
  }

 @GetMapping(SUCCESS_URL)
 public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
    try {
        Payment payment = paypalService.executePayment(paymentId, payerId);
        System.out.println(payment.toJSON());
        if (payment.getState().equals("approved")) {
            return "success";
        }
    } catch (PayPalRESTException e) {
     System.out.println(e.getMessage());
    }
    return "redirect:/";
  }
}
