package com.marcel.malewski.focustimetrackerapi.entity.person.timer;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.TimerBasicFields;

public record TimerFocusAfterBreakDto(
	String timerSelectedTopic,
	Integer timerShortBreak,
	Integer timerLongBreak,
	Boolean timerAutoBreak
) implements TimerBasicFields {
}
