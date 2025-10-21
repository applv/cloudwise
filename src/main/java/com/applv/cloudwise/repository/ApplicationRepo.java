package com.applv.cloudwise.repository;

import com.applv.cloudwise.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepo extends JpaRepository<Application, Integer> {

}
