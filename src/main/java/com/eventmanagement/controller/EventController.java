package com.eventmanagement.controller;

import com.eventmanagement.dto.request.CreateEventRequest;
import com.eventmanagement.dto.response.EventResponse;
import com.eventmanagement.service.EventService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com. eventmanagement.entity.EventType;
import com.eventmanagement.entity.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create Event
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        EventResponse savedEvent = eventService.createEvent(request);
        return ResponseEntity.status(201).body(savedEvent);
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // Get completed events
    @GetMapping("/completed")
    public ResponseEntity<List<EventResponse>> getCompletedEvents() {
        return ResponseEntity.ok(eventService.getCompletedEvents());
    }

    // Get upcoming events
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    // Get event by id
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById( @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // Get events by location
    @GetMapping("/location")
    public ResponseEntity<List<EventResponse>> getEventsByLocation(@Validated @RequestParam("location") String location) {
        return ResponseEntity.ok(eventService.getEventsByLocation(location));
    }

    // Flexible search with filters
    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> searchEvents(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) EventType type,
            @RequestParam(required = false) Status status
    ){
        LocalDate localDate = date != null ? LocalDate.parse(date) : null;
        LocalTime localTime = time != null ? LocalTime.parse(time) : null;
        List<EventResponse> results = eventService.searchEvents(localDate, localTime, location, type, status);
        return ResponseEntity.ok(results);
    }

    // Updating the required information
    @PatchMapping("/{id}")
    public ResponseEntity<EventResponse> updateEventPartially(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        EventResponse updatedEvent = eventService.updateEventPartially(id, updates);
        return ResponseEntity.ok(updatedEvent);
    }

    // Deleting events by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}

