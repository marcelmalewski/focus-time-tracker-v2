package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TimerSettingsDto(
    @Nullable
    String timerSelectedTopic,
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
    Integer timerSetSeconds,
    @Min(1)
    @Max(59)
    @NotNull
    Integer timerShortBreak,
    @Min(1)
    @Max(59)
    @NotNull
    Integer timerLongBreak,
    @NotNull
    @NotNull
    Boolean timerAutoBreak,
    @Min(1)
    @Max(99)
    @NotNull
    Integer timerInterval
) {
}

