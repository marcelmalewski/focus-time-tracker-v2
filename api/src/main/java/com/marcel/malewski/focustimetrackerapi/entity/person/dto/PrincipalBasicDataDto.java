package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import com.marcel.malewski.focustimetrackerapi.validation.space.WithoutSpacesOnTheSides;
import com.marcel.malewski.focustimetrackerapi.validation.space.WithoutSpacesWithinAWord;
import jakarta.validation.constraints.NotNull;

public record PrincipalBasicDataDto(
    Long id,
    @WithoutSpacesWithinAWord
    @WithoutSpacesOnTheSides
    String login,
    @WithoutSpacesWithinAWord
    @WithoutSpacesOnTheSides
    String email,
    Stage timerStage,
    String timerSelectedTopic,
    @NotNull
    Integer timerSetHours,
    @NotNull
    Integer timerSetMinutes,
    @NotNull
    Integer timerSetSeconds,
    @NotNull
    Integer timerShortBreak,
    @NotNull
    Integer timerLongBreak,
    @NotNull
    Boolean timerAutoBreak,
    @NotNull
    Integer timerInterval,
    Integer timerRemainingFocus,
    Integer timerRemainingInterval
) {
}
