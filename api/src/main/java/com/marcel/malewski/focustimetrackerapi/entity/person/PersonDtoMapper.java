package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalBasicDataWithMainTopicsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PersonDtoMapper {
	public TimerPauseDto toTimerPauseDto(PrincipalBasicDataWithMainTopicsDto principalBasicDataWithMainTopicsDto) {
		if (principalBasicDataWithMainTopicsDto == null) {
			return null;
		}

		String setTimePretty = principalBasicDataWithMainTopicsDto.timerSetHours() + "h " +
			principalBasicDataWithMainTopicsDto.timerSetMinutes() + "m " +
			principalBasicDataWithMainTopicsDto.timerSetSeconds() + "s";

		int remainingTime = principalBasicDataWithMainTopicsDto.timerRemainingTime();
		int remainingTimeSeconds = remainingTime % 60;
		int remainingTimeMinutes = (remainingTime / 60) % 60;
		int remainingTimeHours = remainingTime / 60 / 60;

		String remainingTimePretty = remainingTimeHours + "h " +
			remainingTimeMinutes + "m " +
			remainingTimeSeconds + "s";


		return new TimerPauseDto(
			setTimePretty,
			remainingTimePretty,
			principalBasicDataWithMainTopicsDto.timerRemainingTime(),
			principalBasicDataWithMainTopicsDto.timerSelectedTopic(),
			principalBasicDataWithMainTopicsDto.timerShortBreak(),
			principalBasicDataWithMainTopicsDto.timerLongBreak(),
			principalBasicDataWithMainTopicsDto.timerAutoBreak()
		);
	}
}
