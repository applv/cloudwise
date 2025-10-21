package com.applv.cloudwise.dao.mapper;

import com.applv.cloudwise.dao.InstitutionDao;
import com.applv.cloudwise.dao.UserDao;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("UserMapper")
public class UserMapper implements Mapper<UserDao, User> {

  private final Mapper<InstitutionDao, Institution> schoolMapper;

  public UserMapper(@Qualifier("InstitutionMapper") Mapper<InstitutionDao, Institution> schoolMapper) {
    this.schoolMapper = schoolMapper;
  }

  @Override
  public UserDao toDao(User entity) {
    return UserDao
        .builder()
        .name(entity.getName())
        .school(schoolMapper.toDao(entity.getSchool()))
        .build();
  }

  @Override
  public User toEntity(UserDao dao) {
    var entity = new User();
    entity.setName(dao.getName());
    entity.setSchool(schoolMapper.toEntity(dao.getSchool()));

    return entity;
  }

}
