package com.marcel.malewski.focustimetrackerapi.entity.person.interfaces;

import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopicBasicDataDto;

import java.util.List;

public interface PrincipalWithMainTopics extends PrincipalBasicData {
    List<MainTopicBasicDataDto> mainTopicsBasicDataDtos();
}
