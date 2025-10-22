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
public class ApplicationDto {

  private Integer id;

  private String name;

  private String appId;

  private String url;

  private InstitutionDto institution;
}
