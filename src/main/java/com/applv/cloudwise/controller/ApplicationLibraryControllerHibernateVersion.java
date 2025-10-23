package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.service.ApplicationService;
import com.applv.cloudwise.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-apps")
@RequiredArgsConstructor
public class ApplicationLibraryControllerHibernateVersion {

  private final UserService userService;
  private final ApplicationService appService;

  @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Tag(name = "User Apps (Hibernate impl)", description = "<i><b>Returns Student associated applications.</b>")
  public List<ApplicationDto> getAllUserApplication(@PathVariable String username) {
    var user = userService.getUser(username);

    return appService.getApplications(user);
  }
}
