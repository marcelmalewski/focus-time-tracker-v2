package com.marcel.malewski.focustimetrackerapi.validation.space;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpaceOnTheSidesValidator implements ConstraintValidator<WithoutSpacesOnTheSides, String> {
	@Override
	public boolean isValid(String string, ConstraintValidatorContext context) {
		if (string == null || string.isBlank()) {
			return true;
		}

		String trimmedString = string.trim();
		if(string.length() != trimmedString.length()) {
			return false;
		}

		return true;
	}
}
