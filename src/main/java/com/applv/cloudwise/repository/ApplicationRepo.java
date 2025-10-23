package com.applv.cloudwise.repository;

import com.applv.cloudwise.entity.Application;
import com.applv.cloudwise.entity.Institution;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepo extends JpaRepository<Application, Integer> {

  List<Application> findAllByAppKey(String appId);

  List<Application> findApplicationsByInstitutionIn(Collection<Institution> institutions);
}
