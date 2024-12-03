package com.marcel.malewski.focustimetrackerapi.entity.person.timer;

// TODO walidacja nulli?
public record TimerPauseDto(
	String setTimeAsString,
	String timerRemainingTimeAsString,
	Integer timerRemainingTime,
	String timerSelectedTopic,
	Integer timerShortBreak,
	Integer timerLongBreak,
	Boolean timerAutoBreak
) {
}
