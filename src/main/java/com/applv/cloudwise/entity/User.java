package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.SCHOOL_ID;
import static com.applv.cloudwise.entity.Constants.USER;

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
@Table(name = USER)
public class User extends BaseEntity  {

  @ManyToOne
  @JoinColumn(name = SCHOOL_ID, nullable = false)
  private Institution school;
}