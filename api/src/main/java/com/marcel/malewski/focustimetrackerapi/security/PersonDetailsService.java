package com.marcel.malewski.focustimetrackerapi.security;

import com.marcel.malewski.focustimetrackerapi.entity.person.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {
  private final PersonRepository personRepository;

  public PersonDetailsService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String loginOrEmail) {
    return personRepository.findByLoginOrEmail(loginOrEmail, loginOrEmail)
      .orElseThrow(() -> new UsernameNotFoundException(loginOrEmail));
  }
}
