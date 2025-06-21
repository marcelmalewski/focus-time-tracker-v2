package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.TopicBasicData;

import java.util.List;

public record PrincipalWithMainTopicsDto(
    PrincipalBasicDataDto principalBasicData,
    List<TopicBasicData> mainTopicsBasicData
) {
}
