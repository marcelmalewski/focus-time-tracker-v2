package com.marcel.malewski.focustimetrackerapi.entity.person.interfaces;

import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.TopicBasicData;

import java.util.List;

public interface PrincipalWithMainTopics {
    PrincipalBasicData principalBasicData();

    List<TopicBasicData> mainTopicsBasicData();
}
