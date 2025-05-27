package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import jakarta.validation.constraints.NotNull;

public record TimerStageAndRemainingDto(
    @NotNull
    Stage timerStage,
    int timerRemainingTime
) {
}

