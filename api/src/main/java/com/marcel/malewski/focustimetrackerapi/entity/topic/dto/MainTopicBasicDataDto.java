package com.marcel.malewski.focustimetrackerapi.entity.topic.dto;

import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.MainTopicBasicData;

// TODO walidacja
public record MainTopicBasicDataDto(
    Long id,
    String name
) implements MainTopicBasicData {
}
