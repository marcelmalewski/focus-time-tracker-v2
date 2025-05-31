package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerSettings;
import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TimerSettingsDto(
    @NotNull
    Stage timerStage,
    @Nullable
    String timerSelectedTopic,
    @Min(0)
    @Max(99)
    int timerSetHours,
    @Min(0)
    @Max(59)
    int timerSetMinutes,
    @Min(0)
    @Max(59)
    int timerSetSeconds,
    @Min(1)
    @Max(59)
    int timerShortBreak,
    @Min(1)
    @Max(59)
    int timerLongBreak,
    boolean timerAutoBreak,
    @Min(1)
    @Max(99)
    int timerInterval,
    int timerRemainingTime
) implements TimerSettings {
}

