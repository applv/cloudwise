package com.applv.cloudwise.service.impl;

import com.applv.cloudwise.service.InstitutionService;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.dto.mapper.Mapper;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.InstitutionType;
import com.applv.cloudwise.repository.InstitutionRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  @Override
  public List<InstitutionDto> getInstitutions() {
    return institutionRepo.findAll()
        .stream()
        .map(institutionMapper::toDto)
        .distinct()
        .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public InstitutionDto getInstitution(Integer id) {
    return institutionRepo.findById(id).map(institutionMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Institution with id = " + id + " not found"));
  }

  @Transactional(readOnly = true)
  @Override
  public List<InstitutionDto> getInstitutions(InstitutionTypeDto type) {
    return institutionRepo.findAllByType(institutionTypeMapper.toEntity(type))
        .stream()
        .map(institutionMapper::toDto)
        .distinct()
        .toList();
  }

  @Transactional
  @Override
  public InstitutionDto createInstitution(InstitutionDto institutionDto) {
    var institution = institutionMapper.toEntity(institutionDto);
    var newInstitution = institutionRepo.save(institution);

    return institutionMapper.toDto(newInstitution);
  }
}
