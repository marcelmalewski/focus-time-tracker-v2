package com.marcel.malewski.focustimetrackerapi.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.security.Principal;

//TODO to mogą być statyczne metody
@Component
public class SecurityHelper {
    public long extractIdFromPrincipal(Principal principal) {
        String personIdAsString = principal.getName();
        return Long.parseLong(personIdAsString);
    }

    public void logoutManually(HttpServletRequest request,
                               HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, auth);
    }
}
