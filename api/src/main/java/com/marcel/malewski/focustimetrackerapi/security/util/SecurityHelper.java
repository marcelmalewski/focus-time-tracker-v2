package com.marcel.malewski.focustimetrackerapi.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class SecurityHelper {
  public void logoutManually(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    securityContextLogoutHandler.logout(request, response, auth);
  }
}
