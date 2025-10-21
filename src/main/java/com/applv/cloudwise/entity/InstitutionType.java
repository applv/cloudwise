package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.APP_PRIORITY;
import static com.applv.cloudwise.entity.Constants.INSTITUTION_TYPE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = INSTITUTION_TYPE)
public class InstitutionType extends BaseEntity {

  @Column(name = APP_PRIORITY, nullable = false)
  private Integer appPriority;

}