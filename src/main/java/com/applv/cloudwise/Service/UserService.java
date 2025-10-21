package com.applv.cloudwise.Service;

import com.applv.cloudwise.dao.ApplicationDao;
import com.applv.cloudwise.dao.UserDao;
import java.util.List;

public interface UserService {

  List<ApplicationDao> getApplicationsByUser(UserDao user);

  void create(UserDao userDao);

  void update(UserDao userDao);

  void delete(UserDao userDao);
}
