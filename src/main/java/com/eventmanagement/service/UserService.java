package com.eventmanagement.service;

import com.eventmanagement.entity.User;
import com.eventmanagement.entity.Role;
import com.eventmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
        user.setRole(Role.ATTENDEE); // default role
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> updateUser(Long id, User userDetails){
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        });
    }

    public Optional<User> updateUserRole(Long id, Role role){
        return userRepository.findById(id).map(user -> {
            user.setRole(role);
            return userRepository.save(user);
        });
    }
}
