package com.eventmanagement.controller;

import com.eventmanagement.entity.Event;
import com.eventmanagement.service.EventService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")


public class EventController {

    private final EventService eventService;

    
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents(@RequestParam("date") String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return eventService.getUpcomingEvents(dateTime);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }
    
   
    @GetMapping("/location")
        public List<Event> getEventsByLocation(@RequestParam("location") String location) {
        return eventService.getEventsByLocation(location);
    }


    // In EventController.java

    @GetMapping("/search")
    public List<Event> getEventsByLocationIgnoreCase(@RequestParam("location") String location) {
        return eventService.getEventsByLocationIgnoreCase(location);
    }


}
