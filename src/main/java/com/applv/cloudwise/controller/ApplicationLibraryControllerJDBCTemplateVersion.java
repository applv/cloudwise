package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.service.ApplicationLibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/user-apps")
@RequiredArgsConstructor
public class ApplicationLibraryControllerJDBCTemplateVersion {

  private final ApplicationLibraryService applicationLibraryService;

  @Tag(name = "User Apps (JdbcTemplate impl)", description = "<i><b>Returns Student associated applications according <br/>to the priority of the institutions that created those applications.</b>")
  @Operation(summary = "Show list of applications")
  @GetMapping(value = "/all/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getAllUserApplication(@PathVariable String name) {

    return applicationLibraryService.getUserApplications(name);
  }

  @Tag(name = "User School Apps (JdbcTemplate impl)", description = "<i><b>Returns applications created by the school <br/>that is associated with the Student.</b>")
  @GetMapping(value = "/school/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getUserApplication(@PathVariable String name) {

    return applicationLibraryService.getUserSchoolApplications(name);
  }
}
