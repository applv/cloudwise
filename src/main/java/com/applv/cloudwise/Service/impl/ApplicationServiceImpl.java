package com.applv.cloudwise.Service.impl;

import com.applv.cloudwise.Service.ApplicationService;
import com.applv.cloudwise.Service.InstitutionService;
import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.Application;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.repository.ApplicationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
  public ApplicationDto getApplication(Integer id) {
    return applicationRepo.findById(id)
        .map(applicationMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Application with id: " + id + " Not Found"));
  }

  @Override
  public List<ApplicationDto> getApplications(String appId) {
    return applicationRepo.findAllByAppId(appId)
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
}
