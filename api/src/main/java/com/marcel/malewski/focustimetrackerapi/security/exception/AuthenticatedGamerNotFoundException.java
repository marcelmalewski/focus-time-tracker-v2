package com.marcel.malewski.focustimetrackerapi.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AuthenticatedGamerNotFoundException extends RuntimeException {
	public AuthenticatedGamerNotFoundException() {
		super("Authenticated gamer not found");
	}
}
