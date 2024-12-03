package com.marcel.malewski.focustimetrackerapi.entity.person.timer;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerBasicFields;

// TODO walidacja
public record TimerBreakDto(
	String timerSelectedTopic,
	Integer timerShortBreak,
	Integer timerLongBreak,
	Boolean timerAutoBreak
) implements TimerBasicFields {
}
