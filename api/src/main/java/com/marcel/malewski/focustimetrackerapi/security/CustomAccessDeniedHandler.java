package com.marcel.malewski.focustimetrackerapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.stream.Collectors;

//TODO role na potem
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    public static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exc) {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        if (auth != null) {
            LOG.warn("User: {} attempted to access the protected URL: {}", auth.getName(),
                request.getRequestURI());

            String userRolesAString = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
            LOG.warn("User roles: {}", userRolesAString);

            //TODO dodać wypisywanie wymaganych ról i ich przekazanie jako message
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
