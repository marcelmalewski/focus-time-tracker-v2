package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FocusSessionMapper {
    @Mapping(source = "mainTopic.name", target = "mainTopicName")
    FocusSessionBasicDataDto toFocusSessionBasicDataDto(FocusSession focusSession);

    List<FocusSessionBasicDataDto> toFocusSessionBasicDataDtos(List<FocusSession> focusSessions);
}
