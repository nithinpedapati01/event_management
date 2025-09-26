package com.eventmanagement.controller;

import com.eventmanagement.entity.Event;
import com.eventmanagement.service.EventService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Create Event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    // Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Get upcoming events
    @GetMapping("/completed")
    public List<Event> getCompletedEvents() {
        return eventService.getCompletedEvents();
    }

    // Upcoming events after given date
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    // Get event by id
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
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
            return ResponseEntity.badRequest().build();
        }

        List<Event> results = eventService.searchEvents(parsedDate, parsedTime, location, type, status);
        return ResponseEntity.ok(results);
    }

    // updating the requried information
    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEventPartially(
        @PathVariable Long id,
        @RequestBody Map<String, Object> updates
    ) {
    Event updatedEvent = eventService.updateEventPartially(id, updates);
        return ResponseEntity.ok(updatedEvent);
    }


    // Deleting Events by id
    @DeleteMapping  ("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id){
        boolean deleted = eventService.deleteEvent(id);
        if(!deleted){
            return ResponseEntity.status(404).body("Event not found");
        }
        return ResponseEntity.ok(("Event deleted successfully"));
       
    }


}
