package com.applv.cloudwise.dto.mapper;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.entity.Application;
import com.applv.cloudwise.entity.Institution;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller("ApplicationMapper")
public class ApplicationMapper implements Mapper<ApplicationDto, Application> {

  private final Mapper<InstitutionDto, Institution> institutionMapper;

  public ApplicationMapper(@Qualifier("InstitutionMapper") Mapper<InstitutionDto, Institution> institutionMapper) {
    this.institutionMapper = institutionMapper;
  }

  @Override
  public ApplicationDto toDto(Application entity) {
    return ApplicationDto
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .appId(entity.getAppId())
        .url(entity.getUrl())
        .institution(institutionMapper.toDto(entity.getInstitution()))
        .build();
  }

  @Override
  public Application toEntity(ApplicationDto dto) {
    var application = new Application();
    application.setId(dto.getId());
    application.setName(dto.getName());
    application.setAppId(dto.getAppId());
    application.setUrl(dto.getUrl());
    application.setInstitution(institutionMapper.toEntity(dto.getInstitution()));

    return application;
  }
}
