package com.applv.cloudwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class InstitutionDto {

  private Integer id;

  private String name;

  private InstitutionTypeDto institutionType;
}
