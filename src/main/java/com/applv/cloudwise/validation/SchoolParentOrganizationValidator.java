package com.applv.cloudwise.validation;

import static com.applv.cloudwise.entity.Constants.ORGANIZATION;
import static com.applv.cloudwise.entity.Constants.SCHOOL;

import com.applv.cloudwise.dto.InstitutionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.function.Predicate;

public class SchoolParentOrganizationValidator implements ConstraintValidator<ValidSchoolParentOrganization, InstitutionDto> {

  @Override
  public void initialize(ValidSchoolParentOrganization constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(InstitutionDto value, ConstraintValidatorContext context) {
    var parentInstitution = value.getSchoolParentOrganization();
    Predicate<InstitutionDto> isSchool = institutionDto ->
        institutionDto.getType().getName().equals(SCHOOL)
            && Objects.nonNull(parentInstitution)
            && parentInstitution.getType().getName().equals(ORGANIZATION)
            && Objects.isNull(parentInstitution.getSchoolParentOrganization());

    Predicate<InstitutionDto> isOrganization =
        institutionDto -> !institutionDto.getType().getName().equals(SCHOOL) && Objects.isNull(parentInstitution);

    return isSchool.or(isOrganization).test(value);
  }
}
