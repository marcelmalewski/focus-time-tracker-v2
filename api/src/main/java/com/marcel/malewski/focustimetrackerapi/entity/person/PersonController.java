package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalBasicDataDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.UpdateTimerAutoBreakDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class PersonController {
  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }


  @GetMapping(value = "/v1/test")
  @Operation(summary = "Find all persons public info")
  public ResponseEntity<List<PrincipalBasicDataDto>> findAllGamers() {
    List<PrincipalBasicDataDto> allPersons = personService.getAllPersonsBasicData();
    return new ResponseEntity<>(allPersons, HttpStatus.OK);
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
