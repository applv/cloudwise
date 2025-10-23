package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.service.ApplicationLibraryService;
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
@Tag(name = "User Apps (JdbcTemplate impl.)", description = "<i><b>Returns Student associated applications.</b>")
public class ApplicationLibraryControllerJDBCTemplateVersion {

  private final ApplicationLibraryService applicationLibraryService;

  @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getAllUserApplication(@PathVariable String username) {

    return applicationLibraryService.getUserApplications(username);
  }
}
