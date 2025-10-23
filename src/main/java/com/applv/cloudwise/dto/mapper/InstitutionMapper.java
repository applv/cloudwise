package com.applv.cloudwise.dto.mapper;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.InstitutionType;
import java.util.Objects;
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
    var dto = InstitutionDto
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .type(InstitutionTypeMapper.toDto(entity.getType()))
        .build();

    if( Objects.nonNull(entity.getSchoolParentOrganization())) {
      var parent = InstitutionDto
          .builder()
          .id(entity.getSchoolParentOrganization().getId())
          .name(entity.getSchoolParentOrganization().getName())
          .type(InstitutionTypeMapper.toDto(entity.getSchoolParentOrganization().getType()))
          .build();
      dto.setSchoolParentOrganization(parent);
    }
    return dto;
  }

  @Override
  public Institution toEntity(InstitutionDto dto) {

    var entity = new Institution();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setType(InstitutionTypeMapper.toEntity(dto.getType()));

    if (Objects.nonNull(dto.getSchoolParentOrganization())) {
      var parent = new Institution();
      parent.setId(dto.getId());
      parent.setName(dto.getName());
      parent.setType(InstitutionTypeMapper.toEntity(dto.getType()));

      entity.setSchoolParentOrganization(parent);
    }

    return entity;
  }

}
