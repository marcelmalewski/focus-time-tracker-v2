package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalBasicData;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalWithMainTopics;
import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.TopicBasicData;

import java.util.List;

// TODO walidacja
public record PrincipalWithMainTopicsDto(
    PrincipalBasicData principalBasicData,
    List<TopicBasicData> mainTopicsBasicData
) implements PrincipalWithMainTopics {
}
