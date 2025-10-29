package com.applv.cloudwise.dto;

import com.applv.cloudwise.validation.ValidSchoolParentOrganization;
import jakarta.validation.constraints.NotNull;
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
@ValidSchoolParentOrganization
public class InstitutionDto {

  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private InstitutionTypeDto type;

  private InstitutionDto schoolParentOrganization;
}
