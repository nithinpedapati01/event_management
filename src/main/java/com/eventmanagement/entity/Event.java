package com.eventmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Entity

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name cannot be blank")
    private String name;

    @NotBlank(message = "Event location cannot be blank")
    private String location;
    
    @NotNull(message = "Event date and time is required")
    private LocalDateTime dateTime;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity; 
    
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private Status status = Status.UPCOMING;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;



}
