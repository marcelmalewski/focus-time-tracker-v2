package com.marcel.malewski.focustimetrackerapi.entity.person.interfaces;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;

public interface PrincipalBasicData {
    Long id();

    String login();

    String email();

    Stage timerStage();

    String timerSelectedTopic();

    Integer timerSetHours();

    Integer timerSetMinutes();

    Integer timerSetSeconds();

    Integer timerShortBreak();

    Integer timerLongBreak();

    Boolean timerAutoBreak();

    Boolean stopWatchAutoBreak();

    Integer timerRemainingTime();

    Integer timerInterval();
}
