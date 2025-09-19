package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TimerSetTimeDto(
    @Min(0)
    @Max(99)
    @NotNull
    Integer timerSetHours,
    @Min(0)
    @Max(59)
    @NotNull
    Integer timerSetMinutes,
    @Min(0)
    @Max(59)
    @NotNull
    Integer timerSetSeconds
) {
}

