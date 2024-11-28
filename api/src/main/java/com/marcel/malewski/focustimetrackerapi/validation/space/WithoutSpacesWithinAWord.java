package com.marcel.malewski.focustimetrackerapi.validation.space;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SpaceWithinAWordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutSpacesWithinAWord {
	String message() default "must not contain spaces within a word";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
