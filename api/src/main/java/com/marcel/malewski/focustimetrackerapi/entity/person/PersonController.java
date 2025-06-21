package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.TimerSettingsDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.TimerStageAndRemainingTimeDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.TimerStageDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalBasicData;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalWithMainTopics;
import io.swagger.v3.oas.annotations.Operation;
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
    public static final String PERSON_PATH_V1 = "/v1/persons";

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = PERSON_PATH_V1 + "/principal/logged-in")
    @Operation(summary = "Check if person is logged in")
    public ResponseEntity<Boolean> getLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null
            && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken);

        return new ResponseEntity<>(loggedIn, HttpStatus.OK);
    }

    @GetMapping(value = PERSON_PATH_V1 + "/principal/basic-data")
    @Operation(summary = "Get principal basic data")
    public ResponseEntity<PrincipalBasicData> getPrincipalBasicData(Principal principal, HttpServletRequest request,
                                                                    HttpServletResponse response) {
        PrincipalBasicData dto = personService.getPrincipalBasicData(principal, request, response);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = PERSON_PATH_V1 + "/principal/with-main-topics")
    @Operation(summary = "Get principal basic data with main topics")
    public ResponseEntity<PrincipalWithMainTopics> getPrincipalWithMainTopics(Principal principal, HttpServletRequest request,
                                                                              HttpServletResponse response) {
        PrincipalWithMainTopics dto = personService.getPrincipalWithMainTopics(principal, request, response);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PATH_V1 + "/principal/timer")
    public ResponseEntity<Void> updatePrincipalTimerSettings(Principal principal, HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @RequestBody @Valid TimerSettingsDto dto) {
        personService.updatePrincipalTimerSettings(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PATH_V1 + "/principal/timer/stage-and-remaining-focus")
    public ResponseEntity<Void> updatePrincipalTimerStageAndRemainingTime(Principal principal, HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          @RequestBody @Valid TimerStageAndRemainingTimeDto dto) {
        personService.updatePrincipalTimerStageAndRemainingFocus(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PATH_V1 + "/principal/timer/stage-and-remaining-break")
    public ResponseEntity<Void> updatePrincipalTimerStageAndRemainingBreak(Principal principal, HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           @RequestBody @Valid TimerStageAndRemainingTimeDto dto) {
        personService.updatePrincipalTimerStageAndRemainingBreak(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = PERSON_PATH_V1 + "/principal/timer/stage")
    public ResponseEntity<Void> updatePrincipalTimerStage(Principal principal, HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody @Valid TimerStageDto dto) {
        personService.updatePrincipalTimerStage(principal, dto, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
