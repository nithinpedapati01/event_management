package com.eventmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                // Public APIs
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/events").permitAll()
                .requestMatchers("/api/events/upcoming").permitAll()
                .requestMatchers("/api/bookings/completed").permitAll()
                .requestMatchers("/api/bookings/search").permitAll()

                // any other request must be authenticated
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
}

