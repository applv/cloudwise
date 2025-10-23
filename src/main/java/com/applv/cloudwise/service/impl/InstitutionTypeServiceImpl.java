package com.applv.cloudwise.service.impl;

import com.applv.cloudwise.service.InstitutionTypeService;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.InstitutionType;
import com.applv.cloudwise.repository.InstitutionTypeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InstitutionTypeServiceImpl implements InstitutionTypeService {

  private final InstitutionTypeRepo institutionTypeRepo;
  private final Mapper<InstitutionTypeDto, InstitutionType> institutionTypeMapper;

  public InstitutionTypeServiceImpl(InstitutionTypeRepo institutionTypeRepo,
                                    @Qualifier("InstitutionTypeMapper") Mapper<InstitutionTypeDto, InstitutionType>  institutionTypeMapper) {
    this.institutionTypeRepo = institutionTypeRepo;
    this.institutionTypeMapper = institutionTypeMapper;
  }

  @Override
  public InstitutionTypeDto getInstitutionType(String name) {
    return institutionTypeRepo.findByName(name)
        .orElseThrow(() -> new RuntimeException("Institution type with name: " + name + " not found."));
  }

  @Override
  public List<InstitutionTypeDto> getInstitutionTypes() {
    return institutionTypeRepo.findAll()
        .stream()
        .map(institutionTypeMapper::toDto)
        .toList();
  }
}
