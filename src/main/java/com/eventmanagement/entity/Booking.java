package com.eventmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


import java.lang.annotation.Inherited;
import java.time.LocalDateTime;
import lombok.Data;


@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
   
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;
   
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    
    private LocalDateTime bookingDateTime;
    private Long numberOfTickets;
    private String status;
    
}
