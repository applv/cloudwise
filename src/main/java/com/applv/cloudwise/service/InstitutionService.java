package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import java.util.List;

public interface InstitutionService {

  InstitutionDto getInstitution(Integer id);

  List<InstitutionDto> getInstitutions(InstitutionTypeDto type);
}
