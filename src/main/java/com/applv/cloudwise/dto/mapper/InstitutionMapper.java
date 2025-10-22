package com.applv.cloudwise.dto.mapper;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.InstitutionType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("InstitutionMapper")
public class InstitutionMapper implements Mapper<InstitutionDto, Institution> {

  private final Mapper<InstitutionTypeDto, InstitutionType> InstitutionTypeMapper;

  public InstitutionMapper(@Qualifier("InstitutionTypeMapper") Mapper<InstitutionTypeDto, InstitutionType> InstitutionTypeMapper) {
    this.InstitutionTypeMapper = InstitutionTypeMapper;
  }

  @Override
  public InstitutionDto toDto(Institution entity) {

    return InstitutionDto
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .institutionType(InstitutionTypeMapper.toDto(entity.getInstitutionType()))
        .build();
  }

  @Override
  public Institution toEntity(InstitutionDto dto) {
    var entity = new Institution();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setInstitutionType(InstitutionTypeMapper.toEntity(dto.getInstitutionType()));

    return entity;
  }

}
