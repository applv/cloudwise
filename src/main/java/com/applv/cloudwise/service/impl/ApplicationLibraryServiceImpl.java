package com.applv.cloudwise.service.impl;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.repository.ApplicationLibraryRepo;
import com.applv.cloudwise.service.ApplicationLibraryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationLibraryServiceImpl implements ApplicationLibraryService {

  private final ApplicationLibraryRepo repository;

  @Override
  public List<ApplicationDto> getUserApplications(String userName) {
    return repository.getUserApplications(userName);
  }

}
