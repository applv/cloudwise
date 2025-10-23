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

  @GetMapping(value = "/all/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Tag(name = "User Apps (Hibernate impl)", description = "<i><b>Returns Student associated applications according <br/>to the priority of the institutions that created those applications.</b>")
  public List<ApplicationDto> getAllUserApplication(@PathVariable String name) {
    var user = userService.getUser(name);
    return userService.getUserApplications(user);
  }

  @Tag(name = "User School Apps (Hibernate impl)", description = "<i><b>Returns applications created by the school <br/>that is associated with the Student.</b>")
  @GetMapping(value = "/school/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationDto> getUserApplication(@PathVariable String name) {
    var user = userService.getUser(name);
    return userService.getUserSchoolApplications(user);
  }
}
