package com.applv.cloudwise.dto.mapper;

import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.entity.InstitutionType;
import org.springframework.stereotype.Component;

@Component("InstitutionTypeMapper")
public class InstitutionTypeMapper implements Mapper<InstitutionTypeDto, InstitutionType> {

  @Override
  public InstitutionTypeDto toDto(InstitutionType entity) {

    return InstitutionTypeDto
        .builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();
  }

  @Override
  public InstitutionType toEntity(InstitutionTypeDto dto) {
    var entity = new InstitutionType();
    entity.setId(dto.getId());
    entity.setName(dto.getName());

    return entity;
  }
}
