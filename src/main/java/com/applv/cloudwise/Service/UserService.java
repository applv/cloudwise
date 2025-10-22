package com.applv.cloudwise.Service;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.UserDto;
import java.util.List;

public interface UserService {

  List<ApplicationDto> getUserApplications(UserDto user);

  UserDto getUser(String name);

  UserDto getUser(Integer id);

  UserDto create(UserDto userDto);

  void update(UserDto userDto);

  void delete(UserDto userDto);
}
