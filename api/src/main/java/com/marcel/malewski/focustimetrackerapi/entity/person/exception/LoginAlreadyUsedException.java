package com.marcel.malewski.focustimetrackerapi.entity.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO branch test, rejestracja
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginAlreadyUsedException extends RuntimeException {
    public LoginAlreadyUsedException(String login) {
        super("Another person already uses this login, login: " + login);
    }
}
