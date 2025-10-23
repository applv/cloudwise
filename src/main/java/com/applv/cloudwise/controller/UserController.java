package com.applv.cloudwise.controller;

import com.applv.cloudwise.service.UserService;
import com.applv.cloudwise.dto.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

//  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public UserDto getUser(@PathVariable Integer id) {
//    return userService.getUser(id);
//  }
//
//  @GetMapping("/by-name/{name}")
//  public UserDto getUser(@PathVariable String name) {
//    return userService.getUser(name);
//  }
//
//  @PostMapping
//  public void addUser(@RequestBody UserDto user) {
//    userService.create(user);
//  }
//
//  @PutMapping
//  public void updateUser(@RequestBody UserDto userDto) {
//    userService.update(userDto);
//  }
//
//  @DeleteMapping
//  public void deleteUser(@RequestBody UserDto user) {
//     userService.delete(user);
//  }

}
