package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.UserDto;
import java.util.List;

public interface UserService {

  List<UserDto> getUsers();

  UserDto getUser(String name);

  UserDto getUser(Integer id);

  UserDto create(UserDto userDto);

  void update(UserDto userDto);

  void delete(UserDto userDto);
}
