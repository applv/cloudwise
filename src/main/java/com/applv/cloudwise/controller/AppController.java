package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/applications")
@RequiredArgsConstructor
@Tag(name = "Application Controller")
public class AppController {

  private final ApplicationService appService;

  @GetMapping
  public List<ApplicationDto> getApplications() {
    return appService.getApplications();
  }

  @GetMapping("/by-app-key/{key}")
  public List<ApplicationDto> getApp(@PathVariable String key) {
    return appService.getApplications(key);
  }

  @PostMapping
  public ApplicationDto addApp(@RequestBody ApplicationDto appDto, @RequestParam Integer institutionId) {
    appDto.setId(null);
    return appService.createOrUpdateApplication(appDto, institutionId);
  }

  @PutMapping
  public ApplicationDto updateApp(@RequestBody ApplicationDto appDto, @RequestParam Integer institutionId) {
    return appService.createOrUpdateApplication(appDto, institutionId);
  }

  @DeleteMapping("/{appId}")
  public void deleteApp(@PathVariable Integer appId) {
     appService.deleteApplication(appId);
  }

}
