package com.marcel.malewski.focustimetrackerapi.security.util;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;

@Component
@Validated
public class PrincipalExtractor {
	public long extractIdFromPrincipal(@NotNull Principal principal) {
		String gamerIdAsString = principal.getName();
		return Long.parseLong(gamerIdAsString);
	}
}
