package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import jakarta.validation.constraints.NotNull;

public record TimerRemainingTimeDto(
    @NotNull
    int timerRemainingTime
) {
}

