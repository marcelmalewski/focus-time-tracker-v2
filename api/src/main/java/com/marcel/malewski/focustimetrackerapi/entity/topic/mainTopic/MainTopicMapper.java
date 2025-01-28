package com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic;

import com.marcel.malewski.focustimetrackerapi.entity.topic.dto.TopicBasicDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MainTopicMapper {
    TopicBasicDataDto toMainTopicsBasicDataDto(MainTopic mainTopics);
}
