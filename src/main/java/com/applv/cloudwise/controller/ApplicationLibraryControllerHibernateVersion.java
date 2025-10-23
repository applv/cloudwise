package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.ApplicationDto;
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

  @GetMapping(value = "/all/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Tag(name = "User Apps (Hibernate impl)", description = "<i><b>Returns Student associated applications according <br/>to the priority of the institutions that created those applications.</b>")
  public List<ApplicationDto> getAllUserApplication(@PathVariable String username) {
    var user = userService.getUser(username);
    return userService.getUserApplications(user);
  }

  @Tag(name = "User School Apps (Hibernate impl)", description = "<i><b>Returns applications created by the Student associated school.</b>")
  @GetMapping(value = "/school/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getUserApplication(@PathVariable String username) {
    var user = userService.getUser(username);
    return userService.getUserSchoolApplications(user);
  }
}
