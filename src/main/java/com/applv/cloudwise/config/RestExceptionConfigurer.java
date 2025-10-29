package com.applv.cloudwise.config;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class RestExceptionConfigurer {


  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(Throwable ex) {
    String errorMessage;
    HttpStatus status;

    if (ex instanceof NoResourceFoundException) {
      status = HttpStatus.NOT_FOUND;
    } else {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    errorMessage = status + ": " + ex.getMessage();

    Map<String, Object> body = Map.of("status", status, "error", errorMessage);

    return new ResponseEntity<>(body, status);
  }
}
