package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import java.time.LocalDateTime;

public record FocusSessionBasicDataDto(
    Long id,
    LocalDateTime createdAt,
    Boolean finished,
    String mainTopicName
) {
}
