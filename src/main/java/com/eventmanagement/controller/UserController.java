package com.eventmanagement.controller;

import com.eventmanagement.entity.User;
import com.eventmanagement.entity.Role;
import com.eventmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.eventmanagement.exception.ResourceNotFoundException;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){
        User savedUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@Valid @PathVariable String email){
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
        return userService.updateUser(id, userDetails)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestParam Role role){
         return userService.updateUserRole(id, role)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    
}

