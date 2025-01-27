package com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic;

import com.marcel.malewski.focustimetrackerapi.entity.topic.dto.MainTopicBasicDataDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MainTopicMapper {
    List<MainTopicBasicDataDto> toMainTopicsBasicDataDto(List<MainTopic> mainTopics);
}
