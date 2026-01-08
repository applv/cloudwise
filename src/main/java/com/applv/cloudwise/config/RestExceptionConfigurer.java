package com.applv.cloudwise.config;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class RestExceptionConfigurer {

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NoResourceFoundException ex) {

    var status       = HttpStatus.NOT_FOUND;
    var errorMessage = STR."\{status}: \{ex.getMessage()}";

    Map<String, Object> body = Map.of("status", status, "error", errorMessage);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(Exception.class)
  public Map<String, Object> handleInternalServerError(Exception ex) {

    var status       = HttpStatus.INTERNAL_SERVER_ERROR;
    var errorMessage = STR."\{status}: \{ex.getMessage()}";

    return Map.of("status", status, "error", errorMessage);
  }
}
