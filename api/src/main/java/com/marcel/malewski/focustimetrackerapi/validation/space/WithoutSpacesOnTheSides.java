package com.marcel.malewski.focustimetrackerapi.validation.space;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SpaceOnTheSidesValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutSpacesOnTheSides {
  String message() default "must not contain spaces on the sides";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

