package com.example.javawebcourse.service.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
  public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %s not found";

  public ProductNotFoundException(UUID productId) {
    super(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
  }
}
