package com.applv.cloudwise.service;

import com.applv.cloudwise.dto.ApplicationDto;
import java.util.List;

public interface ApplicationLibraryService {

  List<ApplicationDto> getUserApplications(String userName);

}
