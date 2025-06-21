package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;

public record PrincipalBasicDataDto(
    Long id,
    String login,
    String email,
    Stage timerStage,
    String timerSelectedTopic,
    Integer timerSetHours,
    Integer timerSetMinutes,
    Integer timerSetSeconds,
    Integer timerShortBreak,
    Integer timerLongBreak,
    Boolean timerAutoBreak,
    Boolean stopWatchAutoBreak,
    Integer timerBreakTime,
    Integer timerInterval
) {
}
