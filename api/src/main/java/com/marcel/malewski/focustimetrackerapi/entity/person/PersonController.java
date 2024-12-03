package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.UpdateTimerAutoBreakDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class PersonController {
  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
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
