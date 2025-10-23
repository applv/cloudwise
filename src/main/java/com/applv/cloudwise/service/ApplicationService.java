package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.UserDto;
import java.util.List;
import com.applv.cloudwise.dto.InstitutionTypeDto;

public interface ApplicationService {

  List<ApplicationDto> getApplications();

  List<ApplicationDto> getApplications(InstitutionDto institutionDto);

  List<ApplicationDto> getApplications(String appKey);

  List<ApplicationDto> getApplications(InstitutionTypeDto institutionTypeDto);

  List<ApplicationDto> getApplications(UserDto userDto);

  ApplicationDto createOrUpdateApplication(ApplicationDto applicationDto, Integer institutionId);

  void deleteApplication(Integer appId);
}
