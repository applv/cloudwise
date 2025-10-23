package com.applv.cloudwise.repository;

import com.applv.cloudwise.dto.InstitutionTypeDto;
import com.applv.cloudwise.entity.InstitutionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionTypeRepo extends JpaRepository<InstitutionType, Integer> {

  Optional<InstitutionTypeDto> findByName(String name);
}
