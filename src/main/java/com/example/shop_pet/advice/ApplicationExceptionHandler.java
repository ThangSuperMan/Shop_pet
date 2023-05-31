package com.example.shop_pet.advice;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.shop_pet.controllers.UserController;

@RestControllerAdvice
public class ApplicationExceptionHandler {
  Logger logger = LoggerFactory.getLogger(UserController.class);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public HashMap<String, String> handleInvalidArguents(MethodArgumentNotValidException ex) {
    HashMap<String, String> errorMap = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(
        error -> { errorMap.put(error.getField(), error.getDefaultMessage()); });
    return errorMap;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(UsernameNotFoundException.class)
  public HashMap<String, String> handleBusinessException(UsernameNotFoundException ex) {
    HashMap<String, String> errorMap = new HashMap<>();
    errorMap.put("errorMessage", ex.getMessage());
    return errorMap;
  }
}
