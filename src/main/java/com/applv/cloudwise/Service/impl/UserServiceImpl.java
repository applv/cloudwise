package com.applv.cloudwise.Service.impl;

import static com.applv.cloudwise.entity.Constants.SCHOOL;

import com.applv.cloudwise.Service.UserService;
import com.applv.cloudwise.dao.ApplicationDao;
import com.applv.cloudwise.dao.UserDao;
import com.applv.cloudwise.dao.mapper.Mapper;
import com.applv.cloudwise.entity.User;
import com.applv.cloudwise.repository.ApplicationRepo;
import com.applv.cloudwise.repository.UserRepo;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final Mapper<UserDao, User> userMapper;
  private final ApplicationRepo applicationRepo;

  public UserServiceImpl(UserRepo userRepo,
                        @Qualifier("UserMapper") Mapper<UserDao, User> userMapper,
                        ApplicationRepo applicationRepo) {
    this.userRepo        = userRepo;
    this.userMapper      = userMapper;
    this.applicationRepo = applicationRepo;
  }

  @Transactional(readOnly = true)
  @Override
  public List<ApplicationDao> getApplicationsByUser(UserDao userDao) {
    var user = userRepo.findUsersByName(userDao.getName())
        .orElseThrow(() -> new RuntimeException("User with name " + userDao.getName() + " not found"));

    return List.of();
  }

  @Transactional
  @Override
  public void create(UserDao userDao) {
    if (userRepo.findUsersByName(userDao.getName()).isPresent()) {
      throw new RuntimeException("A user with this name " + userDao.getName() + " already exists.");
    }
    validate(userDao);

    userRepo.save(userMapper.toEntity(userDao));
  }

  @Transactional
  @Override
  public void update(UserDao userDao) {
    validate(userDao);
    var user = userRepo.findUsersByName(userDao.getName())
        .orElseThrow(() -> new RuntimeException("User with name " + userDao.getName() + " not found"));
    user.setName(userDao.getName());
    user.setSchool(user.getSchool());

    userRepo.save(user);
  }

  @Transactional
  @Override
  public void delete(UserDao userDao) {
    var user = userRepo.findUsersByName(userDao.getName())
        .orElse(null);
    if (Objects.nonNull(user)) {
      userRepo.delete(user);
    }
  }

  private void validate(UserDao userDao) {
    if (userDao.getSchool().getInstitutionType().getName().equalsIgnoreCase(SCHOOL)) {
      throw new RuntimeException(
          String.format("""
              The user must have a link to the institution type: "%s" but has the type "%s".""",
              SCHOOL, userDao.getSchool().getInstitutionType().getName()));
    }
  }
}
