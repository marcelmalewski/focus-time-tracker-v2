package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class PersonController {
    public static final String PERSON_PRINCIPAL_PATH_V1 = "/v1/persons/principal";

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/logged-in")
    public ResponseEntity<Boolean> getLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null
            && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken);

        return new ResponseEntity<>(loggedIn, HttpStatus.OK);
    }

    @GetMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/basic-data")
    public ResponseEntity<PrincipalBasicDataDto> getPrincipalBasicData(Principal principal, HttpServletRequest request,
                                                                       HttpServletResponse response) {
        PrincipalBasicDataDto dto = personService.getPrincipalBasicData(principal, request, response);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/with-main-topics")
    public ResponseEntity<PrincipalWithMainTopicsDto> getPrincipalWithMainTopics(Principal principal, HttpServletRequest request,
                                                                                 HttpServletResponse response) {
        PrincipalWithMainTopicsDto dto = personService.getPrincipalWithMainTopics(principal, request, response);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/timer")
    public ResponseEntity<Void> updatePrincipalTimerSettings(Principal principal, HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @RequestBody @Valid TimerSettingsDto dto) {
        personService.updatePrincipalTimerSettings(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/timer/stage")
    public ResponseEntity<Void> updatePrincipalTimerStage(Principal principal, HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody @Valid TimerStageDto dto) {
        personService.updatePrincipalTimerStage(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/timer/focus")
    public ResponseEntity<Integer> principalMoveTimerToStageFocus(Principal principal, HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       @RequestBody @Valid TimerSettingsDto dto) {
        int timerRemainingFocus = personService.principalMoveTimerToStageFocus(principal, dto, request, response);
        return new ResponseEntity<>(timerRemainingFocus, HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/timer/pause")
    public ResponseEntity<Integer> principalMoveTimerToStagePause(Principal principal, HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       @RequestBody @Valid TimerCurrentTimeDto dto) {
        int timerRemainingFocus = personService.principalMoveTimerToStagePause(principal, dto, request, response);
        return new ResponseEntity<>(timerRemainingFocus, HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PRINCIPAL_PATH_V1 + "/timer/break")
    public ResponseEntity<Void> principalMoveTimerToStageBreak(Principal principal, HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               @RequestBody @Valid TimerBreakDto dto) {
        personService.principalMoveTimerToStageBreak(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
