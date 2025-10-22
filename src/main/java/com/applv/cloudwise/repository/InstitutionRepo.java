package com.applv.cloudwise.repository;

import com.applv.cloudwise.entity.Institution;
import com.applv.cloudwise.entity.InstitutionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepo extends JpaRepository<Institution, Integer> {

  List<Institution> findAllByInstitutionType(InstitutionType institutionType);
}
