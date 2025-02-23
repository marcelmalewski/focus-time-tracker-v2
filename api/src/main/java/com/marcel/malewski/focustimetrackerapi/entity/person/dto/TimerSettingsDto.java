package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerBasicFields;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerFocusFields;
import com.marcel.malewski.focustimetrackerapi.enums.Stage;

public record TimerSettingsDto(
    Stage timerStage,
    String timerSelectedTopic,
    Integer timerSetHours,
    Integer timerSetMinutes,
    Integer timerSetSeconds,
    Integer timerShortBreak,
    Integer timerLongBreak,
    Boolean timerAutoBreak,
    Integer timerInterval
) implements TimerBasicFields, TimerFocusFields {
}
