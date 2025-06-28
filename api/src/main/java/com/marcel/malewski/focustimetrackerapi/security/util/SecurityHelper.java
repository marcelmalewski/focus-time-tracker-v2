package com.marcel.malewski.focustimetrackerapi.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SecurityHelper {
    public static long extractIdFromPrincipal(Principal principal) {
        String personIdAsString = principal.getName();
        return Long.parseLong(personIdAsString);
    }

    public static void logoutManually(HttpServletRequest request,
                                      HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, auth);
    }
}
