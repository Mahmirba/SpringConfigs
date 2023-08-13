package org.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        if (authException instanceof DisabledException)
            response.sendError(HttpServletResponse.SC_CONFLICT, "User Disabled");
        else if (authException instanceof BadCredentialsException)
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Not Valid");
        else if (authException instanceof UsernameNotFoundException)
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "User Not Found");
        else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
