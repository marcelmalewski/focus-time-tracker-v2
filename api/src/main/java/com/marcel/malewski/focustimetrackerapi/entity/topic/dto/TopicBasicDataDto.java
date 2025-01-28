package com.marcel.malewski.focustimetrackerapi.entity.topic.dto;

import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.TopicBasicData;

// TODO walidacja
public record TopicBasicDataDto(
    Long id,
    String name
) implements TopicBasicData {
}
