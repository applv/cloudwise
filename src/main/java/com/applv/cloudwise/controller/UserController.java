package com.applv.cloudwise.controller;

import com.applv.cloudwise.Service.UserService;
import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(value = "/applications/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getUserApplication(@PathVariable String name) {
    var user = userService.getUser(name);
    return userService.getUserApplications(user);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getUser(@PathVariable Integer id) {
    return userService.getUser(id);
  }

  @GetMapping("/by-name/{name}")
  public UserDto getUser(@PathVariable String name) {
    return userService.getUser(name);
  }

  @PostMapping
  public void addUser(@RequestBody UserDto user) {
    userService.create(user);
  }

  @PutMapping
  public void updateUser(@RequestBody UserDto user) {
    userService.update(user);
  }

  @DeleteMapping
  public void deleteUser(@RequestBody UserDto user) {
     userService.delete(user);
  }

}
