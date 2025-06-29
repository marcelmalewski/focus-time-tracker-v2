package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import jakarta.validation.constraints.NotNull;

public record TimerCurrentTimeDto(
    @NotNull
    int timerCurrentHour,
    @NotNull
    int timerCurrentMinute,
    @NotNull
    int timerCurrentSecond
) {
}

