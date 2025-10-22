package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import java.util.List;

public interface InstitutionService {

     List<InstitutionDto> getInstitutions(InstitutionTypeDto type);
}
