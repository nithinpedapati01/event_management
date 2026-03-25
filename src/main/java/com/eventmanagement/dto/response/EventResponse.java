package com.eventmanagement.dto.response;
import com.eventmanagement.entity.Status;

import lombok.Data;

import com.eventmanagement.entity.EventType;

import java.time.LocalDateTime;


@Data
public class EventResponse {
    
    private Long id;

    private String name;

    private String location;

    private LocalDateTime dateTime;

    private int capacity;

    private EventType eventType;

    private Status status;

    private String organizerName;


}
