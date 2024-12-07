package com.marcel.malewski.focustimetrackerapi.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProfileChecker {

  private final Environment environment;

  @Autowired
  public ProfileChecker(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public CommandLineRunner profileCheckerRunner() {
    return args -> {
      if (environment.getActiveProfiles().length == 0) {
        throw new IllegalStateException("No profile set. Please set one of the following profiles: dev, test, prod (see README)");
      }
    };
  }
}
