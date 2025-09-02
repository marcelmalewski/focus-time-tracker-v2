package com.marcel.malewski.focustimetrackerapi.exception;

public class StageNotFoundException extends RuntimeException {
    public StageNotFoundException(String stageName) {
        super("Unknown stage name: " + stageName);
    }
}
