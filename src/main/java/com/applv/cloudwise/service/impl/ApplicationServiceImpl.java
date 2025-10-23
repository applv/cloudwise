package com.applv.cloudwise.service.impl;

import static com.applv.cloudwise.entity.Constants.ROOT;

import com.applv.cloudwise.dto.UserDto;
import com.applv.cloudwise.service.ApplicationService;
import com.applv.cloudwise.service.InstitutionService;
import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.Application;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.repository.ApplicationRepo;
import com.applv.cloudwise.service.InstitutionTypeService;
import com.applv.cloudwise.service.UserService;
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
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepo applicationRepo;
  private final InstitutionService institutionService;
  private final InstitutionTypeService institutionTypeService;
  private final UserService userService;
  private final Mapper<ApplicationDto, Application> applicationMapper;
  private final Mapper<InstitutionDto, Institution> institutionMapper;

  public ApplicationServiceImpl(ApplicationRepo applicationRepo,
                                InstitutionService institutionService,
                                InstitutionTypeService institutionTypeService,
                                UserService userService,
                                @Qualifier("ApplicationMapper") Mapper<ApplicationDto, Application> applicationMapper,
                                @Qualifier("InstitutionMapper") Mapper<InstitutionDto, Institution> institutionMapper) {
    this.applicationRepo    = applicationRepo;
    this.institutionService = institutionService;
    this.institutionTypeService = institutionTypeService;
    this.userService        = userService;
    this.applicationMapper  = applicationMapper;
    this.institutionMapper  = institutionMapper;
  }

  @Override
  public List<ApplicationDto> getApplications() {
    return applicationRepo.findAll()
        .stream()
        .map(applicationMapper::toDto)
        .toList();
  }

  @Override
  public List<ApplicationDto> getApplications(InstitutionDto institutionDto) {
    return applicationRepo.findAllByInstitution(institutionMapper.toEntity(institutionDto))
        .stream()
        .map(applicationMapper::toDto)
        .toList();
  }

  @Override
  public List<ApplicationDto> getApplications(String appKey) {
    return applicationRepo.findAllByAppKey(appKey)
        .stream()
        .map(applicationMapper::toDto)
        .toList();
  }

  @Override
  public List<ApplicationDto> getApplications(InstitutionTypeDto institutionTypeDto) {
    var institutions = institutionService.getInstitutions(institutionTypeDto)
        .stream()
        .map(institutionMapper::toEntity)
        .distinct()
        .toList();

    return applicationRepo.findApplicationsByInstitutionIn(institutions)
        .stream()
        .map(applicationMapper::toDto)
        .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<ApplicationDto> getApplications(UserDto userDto) {

    var user = userService.getUser(userDto.getId());
    var school = user.getSchool();
    var apps = new ArrayList<>(getApplicationService().getApplications(school));
    var appKeys = apps.stream()
        .map(ApplicationDto::getAppKey)
        .collect(Collectors.toCollection(HashSet::new));

    getApplicationService().getApplications(school.getSchoolParentOrganization())
        .stream()
        .filter(app -> !appKeys.contains(app.getAppKey()))
        .peek(app -> appKeys.add(app.getAppKey()))
        .forEach(apps::add);

    getApplicationService().getApplications(institutionTypeService.getInstitutionType(ROOT))
        .stream()
        .filter(app -> !appKeys.contains(app.getAppKey()))
        .forEach(apps::add);

    return apps;
  }

  @Transactional
  @Override
  public ApplicationDto createOrUpdateApplication(ApplicationDto appDto, Integer institutionId) {

    if (Objects.isNull(appDto.getAppKey())) {
      throw new RuntimeException("appKey is null");
    }
    if (Objects.isNull(appDto.getName())) {
      throw new RuntimeException("name is null");
    }
    if (Objects.isNull(appDto.getUrl())) {
      throw new RuntimeException("url is null");
    }
    if (Objects.isNull(institutionId)) {
      if (Objects.isNull(appDto.getInstitution()) || Objects.isNull(appDto.getInstitution().getId())) {
        throw new RuntimeException("institution cannot be defined");
      }
      institutionId = appDto.getInstitution().getId();
    }

    var institutionDto = institutionService.getInstitution(institutionId);

    if(!applicationRepo.findByAppKeyAndInstitution(appDto.getAppKey(), institutionMapper.toEntity(institutionDto)).isEmpty()){
      throw new RuntimeException(String.format(
          "Application with app_key=%s and created by %s \"%s\" already exists.",
          appDto.getAppKey(),
          institutionDto.getType().getName(),
          institutionDto.getName()));
    }

    var app = new Application();
    app.setAppKey(appDto.getAppKey());
    app.setName(appDto.getName());
    app.setUrl(appDto.getUrl());
    app.setInstitution(institutionMapper.toEntity(institutionDto));

    var newApp = applicationRepo.save(app);

    return applicationMapper.toDto(newApp);
  }

  @Override
  public void deleteApplication(Integer appId) {
    applicationRepo.deleteById(appId);
  }

  @Lookup
  public ApplicationService getApplicationService() {
    return null;
  }
}
