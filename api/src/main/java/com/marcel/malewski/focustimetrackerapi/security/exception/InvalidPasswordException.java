package com.marcel.malewski.focustimetrackerapi.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO jak nie potrzebny to wyrzucić
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password exception");
    }
}
