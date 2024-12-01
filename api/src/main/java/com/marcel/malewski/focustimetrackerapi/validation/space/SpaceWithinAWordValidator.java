package com.marcel.malewski.focustimetrackerapi.validation.space;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpaceWithinAWordValidator implements ConstraintValidator<WithoutSpacesWithinAWord, String> {
  @Override
  public boolean isValid(String string, ConstraintValidatorContext context) {
    if (string == null || string.isBlank()) {
      return true;
    }

    String trimmedString = string.trim();
    if (trimmedString.contains(" ")) {
      return false;
    }

    return true;
  }
}
