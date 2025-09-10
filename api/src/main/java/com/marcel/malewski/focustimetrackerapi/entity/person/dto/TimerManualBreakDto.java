package com.marcel.malewski.focustimetrackerapi.entity.person.dto;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;

public record TimerManualBreakDto(
    Stage breakTypeToStart
) {
}

