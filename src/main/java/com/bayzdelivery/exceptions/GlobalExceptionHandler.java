package com.bayzdelivery.exceptions;

import java.util.AbstractMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
          * Handle invalid role conflict ()
   */
  @ExceptionHandler(InvalidRoleException.class)
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handleInvalidRole(InvalidRoleException exception) {
    LOG.error("Conflict occurred: ", exception);
    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 409
  }

  /**
   * Handle delivery conflict ()
   */
  @ExceptionHandler(DeliveryConflictException.class)
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handleDeliveryConflict(DeliveryConflictException exception) {
    LOG.error("Conflict occurred: ", exception);
    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409
  }


  /**
   * Handle user conflict (e.g., same email with different role)
   */
  @ExceptionHandler(UserConflictException.class)
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handleUserConflict(UserConflictException exception) {
    LOG.error("Conflict occurred: ", exception);
    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409
  }

  /**
   * Handle all other exceptions
   */
  @ExceptionHandler
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(Exception exception) {
    LOG.error("Request could not be processed: ", exception);
    AbstractMap.SimpleEntry<String, String> response =
            new AbstractMap.SimpleEntry<>("message", "Request could not be processed");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
  }
}