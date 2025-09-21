package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import org.springframework.stereotype.Service;

@Service
public class FocusSessionService {
    private final FocusSessionRepository focusSessionRepository;

    public FocusSessionService(FocusSessionRepository focusSessionRepository) {
        this.focusSessionRepository = focusSessionRepository;
    }

    public FocusSession create(FocusSession focusSession) {
        return focusSessionRepository.save(focusSession);
    }
}
