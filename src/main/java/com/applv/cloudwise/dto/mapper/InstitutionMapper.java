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

  public InstitutionMapper(
      @Qualifier("InstitutionTypeMapper") Mapper<InstitutionTypeDto, InstitutionType> InstitutionTypeMapper) {
    this.InstitutionTypeMapper = InstitutionTypeMapper;
  }

  @Override
  public InstitutionDto toDto(Institution entity) {
    var dto = InstitutionDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .type(InstitutionTypeMapper.toDto(entity.getType()))
        .build();

    var parentEntity = entity.getSchoolParentOrganization();
    if (Objects.nonNull(parentEntity)) {
      if (Objects.nonNull(parentEntity.getSchoolParentOrganization())) {
        throw new RuntimeException("The schoolParentOrganization must be null for: %s".formatted(parentEntity));
      }
      dto.setSchoolParentOrganization(toDto(parentEntity));
    }
    return dto;
  }

  @Override
  public Institution toEntity(InstitutionDto dto) {

    var entity = new Institution();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setType(InstitutionTypeMapper.toEntity(dto.getType()));

    var parentDto = dto.getSchoolParentOrganization();
    if (Objects.nonNull(parentDto)) {
      if (Objects.nonNull(parentDto.getSchoolParentOrganization())) {
        throw new RuntimeException("The schoolParentOrganization must be null for: %s".formatted(parentDto));
      }
      entity.setSchoolParentOrganization(toEntity(parentDto));
    }

    return entity;
  }

}
