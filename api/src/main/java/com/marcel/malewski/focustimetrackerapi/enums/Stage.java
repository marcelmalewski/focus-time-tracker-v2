package com.marcel.malewski.focustimetrackerapi.enums;

public enum Stage {
  HOME("Home"),
  FOCUS("Focus"),
  PAUSE("Pause"),
  SHORT_BREAK("Short break"),
  LONG_BREAK("Long break");

  private final String stageName;

  Stage(String stageName) {
    this.stageName = stageName;
  }

  public String getStageName() {
    return stageName;
  }
}
