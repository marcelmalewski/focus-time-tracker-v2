package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcelmalewski.focustimetracker.entity.person.dto.UpdateTimerAutoBreakDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class PersonController {
  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @PatchMapping(value = "person/timerAutoBreak")
  public String updateTimerAutoBreak(Principal principal, HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody UpdateTimerAutoBreakDto updateTimerAutoBreakDto) {
    long principalId = Long.parseLong(principal.getName());
    boolean timerAutoBreak = updateTimerAutoBreakDto.timerAutoBreakInput() != null;

    personService.updatePrincipalTimerAutoBreak(principalId, timerAutoBreak, request, response);

    model.addAttribute("timerAutoBreakPretty", timerAutoBreak ? "On" : "Off");
    model.addAttribute("timerAutoBreak", timerAutoBreak);
    model.addAttribute("interval", updateTimerAutoBreakDto.timerInterval());

    return "timer/fragments/timerAutoBreakSettings";
  }
}
