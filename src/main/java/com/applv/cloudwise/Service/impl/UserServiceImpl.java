package com.applv.cloudwise.Service.impl;

import static com.applv.cloudwise.entity.Constants.SCHOOL;

import com.applv.cloudwise.Service.ApplicationService;
import com.applv.cloudwise.Service.InstitutionTypeService;
import com.applv.cloudwise.Service.UserService;
import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.UserDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.User;
import com.applv.cloudwise.repository.UserRepo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final InstitutionTypeService institutionTypeService;
  private final ApplicationService appService;
  private final Mapper<UserDto, User> userMapper;

  public UserServiceImpl(UserRepo userRepo,
                        InstitutionTypeService institutionTypeService,
                        ApplicationService appService,
                        @Qualifier("UserMapper") Mapper<UserDto, User> userMapper) {
    this.userRepo               = userRepo;
    this.institutionTypeService = institutionTypeService;
    this.userMapper             = userMapper;
    this.appService             = appService;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDto getUser(String name) {
    return userMapper.toDto(userRepo.findUserByName(name)
        .orElseThrow(() -> new RuntimeException("User with name = \"" + name + "\" not found")));
  }

  @Transactional(readOnly = true)
  @Override
  public UserDto getUser(Integer id) {
    return userMapper.toDto(userRepo.findUserById(id)
        .orElseThrow(() -> new RuntimeException("User with id = \"" + id + "\" not found")));
  }

  @Transactional(readOnly = true)
  @Override
  public List<ApplicationDto> getUserApplications(UserDto userDto) {

    var user = userMapper.toDto(userRepo.findUserByName(userDto.getName())
        .orElseThrow(() -> new RuntimeException("User with name \"" + userDto.getName() + "\" not found")));
    var schoolId = user.getSchool().getId();
    var schoolType = user.getSchool().getInstitutionType();
    var apps = new ArrayList<>(appService.getApplications(schoolType)
                                  .stream()
                                  .filter(app -> app.getInstitution().getId().equals(schoolId))
                                  .toList());
    var appIds = apps.stream()
                     .map(ApplicationDto::getAppId)
                     .collect(Collectors.toCollection(HashSet::new));

    var institutionTypes = institutionTypeService.getInstitutionType()
        .stream()
        .filter(type -> !type.getAppPriority().equals(schoolType.getAppPriority()))
        .sorted()
        .toList();

    for(InstitutionTypeDto type: institutionTypes) {
      apps.addAll(
          appService.getApplications(type)
          .stream()
          .filter(app -> !appIds.contains(app.getAppId()))
          .peek(app -> appIds.add(app.getAppId()))
          .toList());
    }
    return apps;
  }

  @Transactional
  @Override
  public UserDto create(UserDto userDto) {
    if ((Objects.nonNull(userDto.getId()) && userRepo.findUserById(userDto.getId()).isPresent()) ||
        userRepo.findUserByName(userDto.getName()).isPresent()) {
      throw new RuntimeException("A user: " + userDto + " already exists.");
    }
    validate(userDto);

    var user = userRepo.save(userMapper.toEntity(userDto));
    return userMapper.toDto(user);
  }

  @Transactional
  @Override
  public void update(UserDto userDto) {
    validate(userDto);
    var user = userRepo.findUserByName(userDto.getName())
        .orElseThrow(() -> new RuntimeException("User with name " + userDto.getName() + " not found"));
    user.setName(userDto.getName());
    user.setSchool(user.getSchool());

    userRepo.save(user);
  }

  @Transactional
  @Override
  public void delete(UserDto userDto) {
    var user = userRepo.findUserByName(userDto.getName())
        .orElse(null);
    if (Objects.nonNull(user)) {
      userRepo.delete(user);
    }
  }

  private void validate(UserDto userDto) {
    if (userDto.getSchool().getInstitutionType().getName().equalsIgnoreCase(SCHOOL)) {
      throw new RuntimeException(
          String.format("""
              The user must have a link to the institution type: "%s" but has the type "%s".""",
              SCHOOL, userDto.getSchool().getInstitutionType().getName()));
    }
  }
}
