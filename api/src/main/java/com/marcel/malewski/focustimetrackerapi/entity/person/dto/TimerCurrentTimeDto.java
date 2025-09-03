package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import jakarta.validation.constraints.NotNull;

public record TimerCurrentTimeDto(
    @NotNull
    Integer timerCurrentHour,
    @NotNull
    Integer timerCurrentMinute,
    @NotNull
    Integer timerCurrentSecond
) {
}

