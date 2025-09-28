package com.eventmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Data;

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
    private LocalDateTime dateTime;
    @NotNull(message = "Capacity cannot be 0")
    private int capacity; 
    
    @NotBlank(message = "please mention event type")
    private String type;

    @NotBlank(message = "Status is required")
    private String status;



}
