package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.service.ApplicationLibraryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/user")
@RequiredArgsConstructor
public class ApplicationLibraryController {

  private final ApplicationLibraryService applicationLibraryService;

  @GetMapping(value = "/applications/all/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getAllUserApplication(@PathVariable String name) {

    return applicationLibraryService.getUserApplications(name);
  }

  @GetMapping(value = "/applications/school/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getUserApplication(@PathVariable String name) {

    return applicationLibraryService.getUserSchoolApplications(name);
  }
}
