package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.entity.topic.dto.TopicBasicDataDto;

import java.util.List;

public record PrincipalWithMainTopicsDto(
    PrincipalBasicDataDto principalBasicData,
    List<TopicBasicDataDto> mainTopicsBasicData
) {
}
