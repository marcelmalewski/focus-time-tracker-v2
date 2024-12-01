package com.marcel.malewski.focustimetrackerapi.entity.person.registration;

import com.marcelmalewski.focustimetracker.entity.person.Person;
import com.marcelmalewski.focustimetracker.entity.person.PersonService;
import com.marcelmalewski.focustimetracker.entity.person.exception.EmailAlreadyUsedException;
import com.marcelmalewski.focustimetracker.entity.person.exception.LoginAlreadyUsedException;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
  private final PersonService personService;
  private final BCryptPasswordEncoder passwordEncoder;

  public RegisterService(PersonService personService, BCryptPasswordEncoder passwordEncoder) {
    this.personService = personService;
    this.passwordEncoder = passwordEncoder;
  }


  //TODO probably settings entity should be created here as well
  public void register(@NotNull RegisterRequestDto registerRequestDto) {
    String login = registerRequestDto.getLogin();
    if (personService.existsByLogin(login)) {
      throw new LoginAlreadyUsedException(login);
    }

    String email = registerRequestDto.getEmail();
    if (personService.existsByEmail(email)) {
      throw new EmailAlreadyUsedException(email);
    }

    String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());

    Person newPerson = Person.builder().login(login).password(encodedPassword).email(email).build();

    personService.create(newPerson);
  }
}
