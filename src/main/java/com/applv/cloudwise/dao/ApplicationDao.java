package com.applv.cloudwise.dao;

import com.applv.cloudwise.entity.Institution;
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
public class ApplicationDao {

  private String name;

  private String appId;

  private String url;

  private InstitutionDao institution;
}
