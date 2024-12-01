package com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic;

import org.springframework.stereotype.Service;

@Service
public class MainTopicService {
  private final MainTopicRepository mainTopicRepository;

  public MainTopicService(MainTopicRepository mainTopicRepository) {
    this.mainTopicRepository = mainTopicRepository;
  }

  public MainTopic create(MainTopic mainTopic) {
    return mainTopicRepository.save(mainTopic);
  }
}
