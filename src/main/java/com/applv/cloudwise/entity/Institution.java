package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.APP_PRIORITY;
import static com.applv.cloudwise.entity.Constants.INSTITUTION;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = INSTITUTION)
public class Institution extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = APP_PRIORITY, nullable = false)
  private InstitutionType institutionType;

}
