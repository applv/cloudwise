package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.INSTITUTION_TYPE;

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

}