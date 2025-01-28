package com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic;

import com.marcel.malewski.focustimetrackerapi.entity.topic.dto.TopicBasicDataDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MainTopicMapper {
    List<TopicBasicDataDto> toMainTopicsBasicDataDto(List<MainTopic> mainTopics);
}
