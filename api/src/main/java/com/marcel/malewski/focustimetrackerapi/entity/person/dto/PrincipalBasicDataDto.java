package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerFocusFields;
import com.marcel.malewski.focustimetrackerapi.enums.Stage;

// TODO walidacja
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
  Integer timerRemainingTime,
  Integer timerInterval
) implements TimerFocusFields {
}
