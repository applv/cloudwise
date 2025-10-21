package com.applv.cloudwise.dao.mapper;

import com.applv.cloudwise.dao.InstitutionDao;
import com.applv.cloudwise.dao.InstitutionTypeDao;
import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.InstitutionType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("InstitutionMapper")
public class InstitutionMapper implements Mapper<InstitutionDao, Institution> {

  private final Mapper<InstitutionTypeDao, InstitutionType> typeMapper;

  public InstitutionMapper(@Qualifier("InstitutionTypeMapper") Mapper<InstitutionTypeDao, InstitutionType> typeMapper) {
    this.typeMapper = typeMapper;
  }

  @Override
  public InstitutionDao toDao(Institution entity) {
    return InstitutionDao
        .builder()
        .name(entity.getName())
        .institutionType(typeMapper.toDao(entity.getInstitutionType()))
        .build();
  }

  @Override
  public Institution toEntity(InstitutionDao dao) {
    var entity = new Institution();
    entity.setName(dao.getName());
    entity.setInstitutionType(typeMapper.toEntity(dao.getInstitutionType()));

    return entity;
  }
}
