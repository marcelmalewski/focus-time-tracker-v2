package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.BreakType;
import jakarta.validation.constraints.NotNull;

public record TimerBreakDto(
    BreakType breakTypeToStart,
    Integer timerRemainingInterval,
    @NotNull
    boolean timerAutoBreak
) {
}

