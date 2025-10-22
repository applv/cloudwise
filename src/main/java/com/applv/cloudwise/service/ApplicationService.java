package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.ApplicationDto;
import java.util.List;
import com.applv.cloudwise.dto.InstitutionTypeDto;

public interface ApplicationService {

  ApplicationDto getApplication(Integer id);

  List<ApplicationDto> getApplications(String appId);

  List<ApplicationDto> getApplications(InstitutionTypeDto institutionTypeDto);
}
