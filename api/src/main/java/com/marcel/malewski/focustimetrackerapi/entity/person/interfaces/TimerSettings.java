package com.marcel.malewski.focustimetrackerapi.entity.person.interfaces;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;

public interface TimerSettings {
    Stage timerStage();

    String timerSelectedTopic();

    int timerSetHours();

    int timerSetMinutes();

    int timerSetSeconds();

    int timerShortBreak();

    int timerLongBreak();

    boolean timerAutoBreak();

    int timerInterval();
}
