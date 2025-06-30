package com.marcel.malewski.focustimetrackerapi.enums;

public enum BreakType {
    SHORT_BREAK("Short break"),
    LONG_BREAK("Long break");

    private final String breakTypeName;

    BreakType(String breakTypeName) {
        this.breakTypeName = breakTypeName;
    }

    public String getBreakTypeName() {
        return breakTypeName;
    }
}
