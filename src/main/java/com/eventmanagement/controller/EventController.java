package com.eventmanagement.controller;

import com.eventmanagement.entity.Event;
import com.eventmanagement.service.EventService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import com.eventmanagement.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create Event
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event savedEvent = eventService.createEvent(event);
        return ResponseEntity.status(201).body(savedEvent);
    }

    // Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Get completed events
    @GetMapping("/completed")
    public List<Event> getCompletedEvents() {
        return eventService.getCompletedEvents();
    }

    // Get upcoming events
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    // Get event by id
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);

        if (event == null) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }

        return ResponseEntity.ok(event);
    }

    // Get events by location
    @GetMapping("/location")
    public List<Event> getEventsByLocation(@RequestParam("location") String location) {
        return eventService.getEventsByLocation(location);
    }

    // Flexible search with filters
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status
    ) {
        LocalDate parsedDate = null;
        LocalTime parsedTime = null;

        try {
            if (date != null && !date.isEmpty()) {
                parsedDate = LocalDate.parse(date); // expects yyyy-MM-dd
            }
            if (time != null && !time.isEmpty()) {
                parsedTime = LocalTime.parse(time); // expects HH:mm
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date or time format. Use yyyy-MM-dd for date and HH:mm for time.");
        }

        List<Event> results = eventService.searchEvents(parsedDate, parsedTime, location, type, status);
        return ResponseEntity.ok(results);
    }

    // Updating the required information
    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEventPartially(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        Event updatedEvent = eventService.updateEventPartially(id, updates);

        if (updatedEvent == null) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }

        return ResponseEntity.ok(updatedEvent);
    }

    // Deleting events by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }
        return ResponseEntity.ok("Event deleted successfully");
    }
}

