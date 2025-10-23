package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.INSTITUTION;
import static com.applv.cloudwise.entity.Constants.SCHOOL_PARENT_ID;
import static com.applv.cloudwise.entity.Constants.TYPE_ID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
  @JoinColumn(name = TYPE_ID, nullable = false)
  private InstitutionType type;

  @ToString.Exclude
  @OneToMany(mappedBy = "institution")
  private List<Application> applications = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = SCHOOL_PARENT_ID)
  private Institution schoolParentOrganization;

  @ToString.Exclude
  @OneToMany(mappedBy = "schoolParentOrganization")
  private List<Institution> schools = new ArrayList<>();
}
