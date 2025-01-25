package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalBasicDataDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.UpdateTimerAutoBreakDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<PrincipalBasicDataDto> getPrincipalBasicData(Principal principal, HttpServletRequest request,
                                                                       HttpServletResponse response) {
        PrincipalBasicDataDto dto = personService.getPrincipalBasicData(principal, request, response);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping(value = "person/timer-auto-break")
    public String updateTimerAutoBreak(Principal principal, HttpServletRequest request,
                                       HttpServletResponse response,
                                       @RequestBody UpdateTimerAutoBreakDto updateTimerAutoBreakDto) {
        long principalId = Long.parseLong(principal.getName());
        boolean timerAutoBreak = updateTimerAutoBreakDto.timerAutoBreakInput() != null;

        personService.updatePrincipalTimerAutoBreak(principalId, timerAutoBreak, request, response);

        return "test";
    }
}
