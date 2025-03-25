package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerBasicFields;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerFocusFields;
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
    @NotNull
    @Min(0)
    @Max(99)
    Integer timerSetHours,
    @NotNull
    @Min(0)
    @Max(59)
    Integer timerSetMinutes,
    @NotNull
    @Min(0)
    @Max(59)
    Integer timerSetSeconds,
    @NotNull
    @Min(1)
    @Max(99)
    Integer timerShortBreak,
    @NotNull
    @Min(1)
    @Max(99)
    Integer timerLongBreak,
    @NotNull
    Boolean timerAutoBreak,
    @NotNull
    @Min(1)
    @Max(99)
    Integer timerInterval
) implements TimerBasicFields, TimerFocusFields {
}

