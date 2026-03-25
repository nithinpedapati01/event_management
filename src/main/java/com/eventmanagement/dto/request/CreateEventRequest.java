
package com.eventmanagement.dto.request;
import com.eventmanagement.entity.EventType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateEventRequest {

    @NotBlank(message = "Event name cannot be blank")
    private String name;

    @NotBlank(message = "Event location cannot be blank")
    private String location;

    @NotNull(message = "Event date and time is required") 
    private String dateTime;
   
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
    
    @NotNull(message = "Event type is required")
    private EventType eventType;
    
}
