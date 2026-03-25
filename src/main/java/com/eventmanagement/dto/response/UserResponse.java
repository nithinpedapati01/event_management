package com.eventmanagement.dto.response;
import com.eventmanagement.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    
    
    private long id; 

    private String UserName;

    private String email;

    private Role role = Role.ATTENDEE;
}
