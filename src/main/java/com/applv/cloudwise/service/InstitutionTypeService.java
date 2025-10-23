package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.InstitutionTypeDto;
import java.util.List;

public interface InstitutionTypeService {

  InstitutionTypeDto getInstitutionType(String name);

  List<InstitutionTypeDto> getInstitutionTypes();
}
