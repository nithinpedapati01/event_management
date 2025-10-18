package com.eventmanagement.controller;

import com.eventmanagement.entity.User;
import com.eventmanagement.entity.Role;
import com.eventmanagement.repository.UserRepository;
import com.eventmanagement.service.CustomUserDetailsService;
import com.eventmanagement.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // 1️⃣ Registration Endpoint
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Default role = ROLE_USER
        user.setRole(Role.ATTENDEE); 
        userRepository.save(user);
        return "User registered successfully";
    }

    // 2️⃣ Login Endpoint
    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        try {
            // Authenticate user using AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }

        // If authentication successful, generate JWT token
        final String token = jwtService.generateToken(user.getEmail());
        return token; // return token to client
    }
}

