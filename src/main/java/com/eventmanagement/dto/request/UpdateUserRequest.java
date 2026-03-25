package com.eventmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank (message = "Username cannot be blank")
    private String userName;
    @NotBlank (message = "Email cannot be blank")
    private String email;  
    @NotBlank (message = "Password cannot be blank") 
    private String password;
}
