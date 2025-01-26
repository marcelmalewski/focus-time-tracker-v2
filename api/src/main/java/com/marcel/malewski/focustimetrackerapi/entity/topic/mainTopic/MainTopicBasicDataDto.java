package com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic;

import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.MainTopicBasicData;

// TODO walidacja
public record MainTopicBasicDataDto(
    Long id,
    String name
) implements MainTopicBasicData {
}
