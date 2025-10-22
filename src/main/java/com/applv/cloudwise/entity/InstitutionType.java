package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.PRIORITY;
import static com.applv.cloudwise.entity.Constants.INSTITUTION_TYPE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Comparator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = INSTITUTION_TYPE)
public class InstitutionType extends BaseEntity implements Comparable<InstitutionType> {

  @Column(name = PRIORITY, nullable = false, unique = true)
  private Integer appPriority;

  @Override
  public int compareTo(@NonNull InstitutionType o) {
    return Comparator.comparing(InstitutionType::getAppPriority).reversed().compare(this, o);
  }
}