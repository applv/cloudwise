package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.APPLICATION;
import static com.applv.cloudwise.entity.Constants.APP_ID;
import static com.applv.cloudwise.entity.Constants.INSTITUTION_ID;
import static com.applv.cloudwise.entity.Constants.NAME;
import static com.applv.cloudwise.entity.Constants.URL;

import jakarta.persistence.Column;
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
@Table(name = APPLICATION)
public class Application extends BaseEntity {

  @Column(name = NAME, nullable = false, length = 200)
  private String name;

  @Column(name = APP_ID, nullable = false, length = 200)
  private String appId;

  @Column(name = URL, nullable = false, length = 200)
  private String url;

  @ManyToOne
  @JoinColumn(name = INSTITUTION_ID, nullable = false)
  private Institution institution;

}
