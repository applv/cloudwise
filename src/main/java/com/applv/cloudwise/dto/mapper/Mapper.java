package com.applv.cloudwise.dto.mapper;

public interface Mapper<D, E> {

  D toDto(E entity);

  E toEntity(D dto);
}
