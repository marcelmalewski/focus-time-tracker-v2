package com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainTopicRepository extends JpaRepository<MainTopic, Long> {
}
