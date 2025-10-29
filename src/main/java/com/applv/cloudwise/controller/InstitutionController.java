package com.applv.cloudwise.controller;

import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.service.InstitutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/institution")
@RequiredArgsConstructor
@Tag(name = "Institution Controller")
public class InstitutionController {

  private final InstitutionService service;

  @GetMapping
  public Iterable<InstitutionDto> getInstitutions() {
    return service.getInstitutions();
  }

  @PostMapping
  public InstitutionDto addInstitution(@Valid @RequestBody InstitutionDto dto) {
    dto.setId(null);
    return service.createInstitution(dto);
  }

}
