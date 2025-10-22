package com.applv.cloudwise.dto.mapper;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.UserDto;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("UserMapper")
public class UserMapper implements Mapper<UserDto, User> {

  private final Mapper<InstitutionDto, Institution> schoolMapper;

  public UserMapper(@Qualifier("InstitutionMapper") Mapper<InstitutionDto, Institution> schoolMapper) {
    this.schoolMapper = schoolMapper;
  }

  @Override
  public UserDto toDto(User entity) {
    return UserDto
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .school(schoolMapper.toDto(entity.getSchool()))
        .build();
  }

  @Override
  public User toEntity(UserDto dto) {
    var entity = new User();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setSchool(schoolMapper.toEntity(dto.getSchool()));

    return entity;
  }

}
