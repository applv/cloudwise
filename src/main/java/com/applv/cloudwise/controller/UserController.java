package com.applv.cloudwise.controller;

import com.applv.cloudwise.Service.UserService;
import com.applv.cloudwise.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PutMapping
  public void addUser(@RequestBody User user) {

  }

}
