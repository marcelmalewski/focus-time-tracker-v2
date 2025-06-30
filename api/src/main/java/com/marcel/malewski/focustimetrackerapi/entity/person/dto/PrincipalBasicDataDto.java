package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import jakarta.validation.constraints.NotNull;

public record PrincipalBasicDataDto(
    @NotNull
    long id,
    String login,
    String email,
    Stage timerStage,
    String timerSelectedTopic,
    @NotNull
    int timerSetHours,
    @NotNull
    int timerSetMinutes,
    @NotNull
    int timerSetSeconds,
    @NotNull
    int timerShortBreak,
    @NotNull
    int timerLongBreak,
    @NotNull
    boolean timerAutoBreak,
    @NotNull
    int timerInterval,
    Integer timerRemainingFocus,
    Integer timerRemainingInterval
) {
}
