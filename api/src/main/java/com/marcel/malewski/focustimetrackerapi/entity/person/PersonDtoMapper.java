package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalWithMainTopicsDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.timer.TimerPauseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PersonDtoMapper {
    public TimerPauseDto toTimerPauseDto(PrincipalWithMainTopicsDto principalWithMainTopicsDto) {
        if (principalWithMainTopicsDto == null) {
            return null;
        }

        String setTimePretty = principalWithMainTopicsDto.timerSetHours() + "h " +
            principalWithMainTopicsDto.timerSetMinutes() + "m " +
            principalWithMainTopicsDto.timerSetSeconds() + "s";

        int remainingTime = principalWithMainTopicsDto.timerRemainingTime();
        int remainingTimeSeconds = remainingTime % 60;
        int remainingTimeMinutes = (remainingTime / 60) % 60;
        int remainingTimeHours = remainingTime / 60 / 60;

        String remainingTimePretty = remainingTimeHours + "h " +
            remainingTimeMinutes + "m " +
            remainingTimeSeconds + "s";


        return new TimerPauseDto(
            setTimePretty,
            remainingTimePretty,
            principalWithMainTopicsDto.timerRemainingTime(),
            principalWithMainTopicsDto.timerSelectedTopic(),
            principalWithMainTopicsDto.timerShortBreak(),
            principalWithMainTopicsDto.timerLongBreak(),
            principalWithMainTopicsDto.timerAutoBreak()
        );
    }
}
