package com.marcel.malewski.focustimetrackerapi.entity.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO branch test, rejestracja
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Another person already uses this email, email: " + email);
    }
}
