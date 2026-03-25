package com.eventmanagement.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    
    @NotNull(message = "Event ID is required")
    private Long eventId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @Min(value = 1, message = "At least 1 ticket must be booked")
    @Max(value = 10, message = "You cannot book more than 10 tickets")
    private int numberOfTickets;

    
}
