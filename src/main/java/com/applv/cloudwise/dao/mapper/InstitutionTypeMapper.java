package com.applv.cloudwise.dao.mapper;

import com.applv.cloudwise.dao.InstitutionTypeDao;
import com.applv.cloudwise.entity.InstitutionType;
import org.springframework.stereotype.Component;

@Component("InstitutionTypeMapper")
public class InstitutionTypeMapper implements Mapper<InstitutionTypeDao, InstitutionType> {

  @Override
  public InstitutionTypeDao toDao(InstitutionType entity) {

    return InstitutionTypeDao
        .builder()
        .name(entity.getName())
        .appPriority(entity.getAppPriority())
        .build();
  }

  @Override
  public InstitutionType toEntity(InstitutionTypeDao dao) {
    var entity = new InstitutionType();
    entity.setName(dao.getName());
    entity.setAppPriority(dao.getAppPriority());

    return entity;
  }
}
