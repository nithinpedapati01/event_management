package com.eventmanagement.service;

import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.dto.request.UserRegisterRequest;
import com.eventmanagement.dto.request.UpdateUserRequest;
import com.eventmanagement.dto.response.UserResponse;
import com.eventmanagement.entity.Role;
import com.eventmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private UserResponse mapToResponse(User user){
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setUserName(user.getUserName());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        return res;
    }

    public UserResponse registerUser(UserRegisterRequest request){

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // will hash later
        user.setRole(Role.ATTENDEE);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
  }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public UserResponse getUserById(Long id){
        return userRepository.findById(id).map(this::mapToResponse).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    return mapToResponse(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public UserResponse updateUserRole(Long id, Role role){
        return userRepository.findById(id).map(user -> {
            user.setRole(role);
            return mapToResponse(userRepository.save(user));
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    public void deleteUser(Long id){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
