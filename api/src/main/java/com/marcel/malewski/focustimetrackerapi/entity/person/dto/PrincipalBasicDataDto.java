package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;

public record PrincipalBasicDataDto(
    long id,
    String login,
    String email,
    Stage timerStage,
    String timerSelectedTopic,
    int timerSetHours,
    int timerSetMinutes,
    int timerSetSeconds,
    int timerShortBreak,
    int timerLongBreak,
    boolean timerAutoBreak,
    int timerInterval,
    Integer timerRemainingFocus,
    Integer timerRemainingInterval,
    Integer timerRemainingBreak
) {
}
