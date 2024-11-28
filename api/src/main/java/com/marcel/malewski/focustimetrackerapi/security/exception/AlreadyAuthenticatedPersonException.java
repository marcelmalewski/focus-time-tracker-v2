package com.marcel.malewski.focustimetrackerapi.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyAuthenticatedPersonException extends RuntimeException {
	public AlreadyAuthenticatedPersonException() {
		super("Person is already authenticated");
	}
}

