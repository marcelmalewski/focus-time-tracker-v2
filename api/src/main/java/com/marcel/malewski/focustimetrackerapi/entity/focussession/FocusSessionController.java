package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class FocusSessionController {
    public static final String FOCUS_SESSION_PATH_V1 = "/v1/focus-sessions/principal";

    private final FocusSessionService focusSessionService;

    public FocusSessionController(FocusSessionService focusSessionService) {
        this.focusSessionService = focusSessionService;
    }

    @GetMapping(value = FOCUS_SESSION_PATH_V1)
    public ResponseEntity<Page<FocusSessionBasicDataDto>> getPrincipalAllFocusSessions(Pageable pageable, Principal principal) {
        Page<FocusSessionBasicDataDto> result = focusSessionService.findAllWithPersonAndMainTopicByPersonId(pageable, principal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
