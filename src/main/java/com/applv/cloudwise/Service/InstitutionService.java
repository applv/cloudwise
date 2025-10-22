package com.applv.cloudwise.Service;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import java.util.List;

public interface InstitutionService {

     List<InstitutionDto> getInstitutions(InstitutionTypeDto type);
}
