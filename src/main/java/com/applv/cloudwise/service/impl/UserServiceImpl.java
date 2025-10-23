package com.applv.cloudwise.service.impl;

import static com.applv.cloudwise.entity.Constants.SCHOOL;

import com.applv.cloudwise.dto.mapper.InstitutionMapper;
import com.applv.cloudwise.service.ApplicationService;
import com.applv.cloudwise.service.InstitutionService;
import com.applv.cloudwise.service.InstitutionTypeService;
import com.applv.cloudwise.service.UserService;
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
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final InstitutionService institutionService;
  private final InstitutionTypeService institutionTypeService;
  private final ApplicationService appService;
  private final Mapper<UserDto, User> userMapper;
  private final InstitutionMapper institutionMapper;

  public UserServiceImpl(UserRepo userRepo,
                        InstitutionService institutionService,
                        InstitutionTypeService institutionTypeService,
                        ApplicationService appService,
                        @Qualifier("UserMapper") Mapper<UserDto, User> userMapper, InstitutionMapper institutionMapper) {
    this.userRepo               = userRepo;
    this.institutionService = institutionService;
    this.institutionTypeService = institutionTypeService;
    this.userMapper             = userMapper;
    this.appService             = appService;
    this.institutionMapper = institutionMapper;
  }

  @Override
  public List<UserDto> getUsers() {
    return userRepo.findAll()
        .stream()
        .map(userMapper::toDto)
        .collect(Collectors.toList());
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
        .orElseThrow(() -> new RuntimeException("User with id = " + id + " not found")));
  }

  @Transactional(readOnly = true)
  @Override
  public List<ApplicationDto> getUserApplications(UserDto userDto) {

    var user = userMapper.toDto(userRepo.findUserByName(userDto.getName())
        .orElseThrow(() -> new RuntimeException("User with name \"" + userDto.getName() + "\" not found")));

    var schoolId = user.getSchool().getId();
    var schoolType = user.getSchool().getType();
    var apps = new ArrayList<>(appService.getApplications(schoolType)
                                  .stream()
                                  .filter(app -> app.getInstitution().getId().equals(schoolId))
                                  .toList());
    var appKeys = apps.stream()
                     .map(ApplicationDto::getAppKey)
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
          .filter(app -> !appKeys.contains(app.getAppKey()))
          .peek(app -> appKeys.add(app.getAppKey()))
          .toList());
    }
    return apps;
  }

  @Transactional(readOnly = true)
  @Override
  public List<ApplicationDto> getUserSchoolApplications(UserDto userDto) {

    var user = userMapper.toDto(userRepo.findUserByName(userDto.getName())
        .orElseThrow(() -> new RuntimeException("User with name \"" + userDto.getName() + "\" not found")));

    var schoolId = user.getSchool().getId();
    var schoolType = user.getSchool().getType();
    return new ArrayList<>(appService.getApplications(schoolType)
        .stream()
        .filter(app -> app.getInstitution().getId().equals(schoolId))
        .toList());
  }

  @Transactional
  @Override
  public UserDto create(UserDto userDto) {

    if (Objects.nonNull(userDto.getId()) && userRepo.findUserById(userDto.getId()).isPresent()) {
      throw new RuntimeException("A user with id = " + userDto.getId() + " already exists.");
    }
    if (userRepo.findUserByName(userDto.getName()).isPresent()) {
      throw new RuntimeException("A user with name = \"" + userDto.getName() + "\" already exists.");
    }

    validate(userDto);
    var schoolDto = institutionService.getInstitution(userDto.getSchool().getId());
    userDto.setSchool(schoolDto);

    var user = userMapper.toEntity(userDto);
    if(Objects.nonNull(user.getId())) {
      user.setId(null);
    }

    var school = institutionMapper.toEntity(schoolDto);
    user.setSchool(school);
    var newUser = userRepo.save(user);

    return userMapper.toDto(newUser);
  }

  @Transactional
  @Override
  public void update(UserDto userDto) {
    var user = getUserService().getUser(userDto.getId());
    user.setName(userDto.getName());
    if(Objects.nonNull(userDto.getSchool()) && !user.getSchool().getId().equals(userDto.getSchool().getId())) {
      var school = institutionService.getInstitution(userDto.getSchool().getId());
      user.setSchool(school);
    }
    validate(user);

    userRepo.save(userMapper.toEntity(user));
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

  @Lookup
  public UserService getUserService() {
    return null;
  }

  private void validate(UserDto userDto) {
    if(Objects.isNull(userDto.getName())) {
      throw new RuntimeException("The user name can't be null.");
    }

    if(Objects.isNull(userDto.getSchool()) || Objects.isNull(userDto.getSchool().getId())) {
      throw new RuntimeException("The school value is null or has wrong values.");
    }
    var school = institutionService.getInstitution(userDto.getSchool().getId());
    if (!school.getType().getName().equals(SCHOOL)) {
      throw new RuntimeException("The school value has wrong type.");
    }
  }
}
