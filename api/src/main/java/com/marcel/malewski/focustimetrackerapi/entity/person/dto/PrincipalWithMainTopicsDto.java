package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalWithMainTopics;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopic;
import com.marcel.malewski.focustimetrackerapi.enums.Stage;

import java.util.List;

// TODO walidacja
public record PrincipalWithMainTopicsDto(
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
    Integer timerInterval,
    List<MainTopic> mainTopics
) implements PrincipalWithMainTopics {
}
