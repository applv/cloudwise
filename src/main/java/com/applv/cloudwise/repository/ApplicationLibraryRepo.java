package com.applv.cloudwise.repository;

import com.applv.cloudwise.dto.ApplicationDto;
import java.util.List;

public interface ApplicationLibraryRepo {

  List<ApplicationDto> getUserApplications(String userName);

}
