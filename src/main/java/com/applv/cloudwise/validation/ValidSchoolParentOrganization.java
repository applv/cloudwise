package com.applv.cloudwise.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SchoolParentOrganizationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSchoolParentOrganization {

  String message() default """
      The schoolParentOrganization value is wrong: instance is not a school but has not NULL schoolParentOrganization value"
      + " or instance is a school but schoolParentOrganization value is NULL or is not an organization
      """;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
