package com.marcel.malewski.focustimetrackerapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

//TODO dodać profile
//TODO co to dokladnie stateless session
//TODO jaki powinien byc dostep do dokumentacji
//TODO obargnac sesjie co to znaczy
//			.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

  //TODO dodac specjalna permisje dostepu do swaggera
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(
          HttpMethod.GET,
          "/docs",
          "/v2/api-docs/**",
          "/v3/api-docs/**",
          "/swagger-resources/**",
          "/swagger-ui/**",
          "/swagger-ui.html"
        )
        .permitAll()

        .requestMatchers(
          HttpMethod.POST,
          "/v1/registration/persons",
          "/v1/registration/moderators"
        )
        .permitAll()

        .requestMatchers(
          "/error",
          //for local testing:
          "/v1/gamers"
        )
        .permitAll()

        .anyRequest()
        .authenticated()
      )

      .formLogin(formLogin -> formLogin
        .successHandler((request, response, authentication) -> {
          // Do nothing upon successful login
        })
      )

      .exceptionHandling(exceptionHandling -> exceptionHandling
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .accessDeniedHandler(accessDeniedHandler())
      );

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

//	@Bean
//	public RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//		String hierarchy =
//			GAMER_ANALYSE_PRIVILEGE + " > " + GAMER_VIEW_PRIVILEGE + "\n" +
//			GAMER_MANAGE_PRIVILEGE + " > " + GAMER_ANALYSE_PRIVILEGE + "\n" +
//			GAMER_MANAGE_PRIVILEGE + " > " + GAMER_CREATE_PRIVILEGE + "\n" +
//			GAMER_MANAGE_PRIVILEGE + " > " + GAMER_EDIT_PRIVILEGE + "\n" +
//			GAMER_MANAGE_PRIVILEGE + " > " + GAMER_DELETE_PRIVILEGE + "\n" +
//			GAMER_MANAGE_PRIVILEGE + " > " + GAMER_PRIVATE_DATA_VIEW_PRIVILEGE + "\n" +
//
//			MODERATOR_MANAGE_PRIVILEGE + " > " + MODERATOR_CREATE_PRIVILEGE + "\n" +
//			MODERATOR_MANAGE_PRIVILEGE + " > " + MODERATOR_DELETE_PRIVILEGE;
//		roleHierarchy.setHierarchy(hierarchy);
//		return roleHierarchy;
//	}

//	@Bean
//	static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
//		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
//		handler.setRoleHierarchy(roleHierarchy);
//		return handler;
//	}
}
