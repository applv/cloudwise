package com.applv.cloudwise.service.impl;

import com.applv.cloudwise.service.ApplicationService;
import com.applv.cloudwise.service.InstitutionService;
import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.Application;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.repository.ApplicationRepo;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepo applicationRepo;
  private final InstitutionService institutionService;
  private final Mapper<ApplicationDto, Application> applicationMapper;
  private final Mapper<InstitutionDto, Institution> institutionMapper;

  public ApplicationServiceImpl(ApplicationRepo applicationRepo,
                                InstitutionService institutionService,
                                @Qualifier("ApplicationMapper") Mapper<ApplicationDto, Application> applicationMapper,
                                @Qualifier("InstitutionMapper") Mapper<InstitutionDto, Institution> institutionMapper) {
    this.applicationRepo    = applicationRepo;
    this.institutionService = institutionService;
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
  public ApplicationDto getApplication(Integer id) {
    return applicationRepo.findById(id)
        .map(applicationMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Application with id: " + id + " Not Found"));
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

}
