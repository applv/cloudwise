package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import java.util.List;
import com.applv.cloudwise.dto.InstitutionTypeDto;

public interface ApplicationService {

  List<ApplicationDto> getApplications();

  ApplicationDto getApplication(Integer id);

  List<ApplicationDto> getApplications(String appKey);

  List<ApplicationDto> getApplications(InstitutionTypeDto institutionTypeDto);

  ApplicationDto createOrUpdateApplication(ApplicationDto applicationDto, Integer institutionId);

  void deleteApplication(Integer appId);
}
