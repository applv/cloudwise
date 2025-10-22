package com.applv.cloudwise.Service.impl;

import com.applv.cloudwise.Service.InstitutionService;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.InstitutionType;
import com.applv.cloudwise.repository.InstitutionRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InstitutionServiceImpl implements InstitutionService {

  private final InstitutionRepo institutionRepo;
  private final Mapper<InstitutionTypeDto, InstitutionType> institutionTypeMapper;
  private final Mapper<InstitutionDto, Institution> institutionMapper;

  public InstitutionServiceImpl(InstitutionRepo institutionRepo,
                                @Qualifier("InstitutionTypeMapper") Mapper<InstitutionTypeDto, InstitutionType> institutionTypeMapper,
                                @Qualifier("InstitutionMapper") Mapper<InstitutionDto, Institution> institutionMapper) {
    this.institutionRepo = institutionRepo;
    this.institutionTypeMapper = institutionTypeMapper;
    this.institutionMapper = institutionMapper;
  }

  @Override
  public List<InstitutionDto> getInstitutions(InstitutionTypeDto type) {
    return institutionRepo.findAllByInstitutionType(institutionTypeMapper.toEntity(type))
        .stream()
        .map(institutionMapper::toDto)
        .distinct()
        .toList();
  }
}
