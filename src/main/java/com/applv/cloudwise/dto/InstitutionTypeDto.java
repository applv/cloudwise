package com.applv.cloudwise.dto;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Builder
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class InstitutionTypeDto  implements Comparable<InstitutionTypeDto> {

  private Integer id;

  private String name;

  private Integer appPriority;

  @Override
  public int compareTo(@NonNull InstitutionTypeDto o) {
    return Comparator.comparing(InstitutionTypeDto::getAppPriority).reversed().compare(this, o);
  }
}
