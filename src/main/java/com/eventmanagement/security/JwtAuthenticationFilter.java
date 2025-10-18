package com.eventmanagement.security;

import com.eventmanagement.service.CustomUserDetailsService;
import com.eventmanagement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ Get the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token found → just continue
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Extract token from "Bearer <token>"
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);

        // 3️⃣ If username is not null and user not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4️⃣ Load user details from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5️⃣ Validate token
            if (jwtService.isTokenValid(jwtToken)) {

                // 6️⃣ Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 7️⃣ Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8️⃣ Pass request to the next filter or controller
        filterChain.doFilter(request, response);
    }
}

