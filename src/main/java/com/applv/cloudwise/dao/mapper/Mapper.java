package com.applv.cloudwise.dao.mapper;

public interface Mapper<D, E> {

  D toDao(E entity);

  E toEntity(D dao);
}
