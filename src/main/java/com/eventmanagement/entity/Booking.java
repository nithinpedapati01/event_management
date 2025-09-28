package com.eventmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import com.eventmanagement.entity.User;
import lombok.Data;


@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
   
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    @NotNull(message = "Event is required")
    private Event event;
   
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @NotNull(message = "User is required")
    private User user;
    
    
    private LocalDateTime bookingDateTime;

    @Min(value = 1, message = "At least 1 ticket must be booked")
    @Max(value = 10, message = "You cannot book more than 10 tickets")
    private Long numberOfTickets;


    @NotBlank(message = "Status is required")
    private String status;
    
}
