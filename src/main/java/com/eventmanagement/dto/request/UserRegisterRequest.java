package com.eventmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class UserRegisterRequest {
    
    @NotBlank(message = "Please enter the UserName")
    private String userName;

    @NotBlank(message="Please enter a email")
    private String email;

    @NotBlank(message = "Please enter a password")
    private String password;

}
