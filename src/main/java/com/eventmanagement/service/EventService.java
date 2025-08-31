package com.eventmanagement.service;

import com.eventmanagement.entity.Event;
import com.eventmanagement.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.eventmanagement.exception.EventNotFoundException;



@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getUpcomingEvents(LocalDateTime dateTime) {
        return eventRepository.findByDateTimeAfter(dateTime);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
    }

    
    public List<Event> getEventsByLocation(String location) {
        List<Event> events = eventRepository.findByLocation(location);
        if (events.isEmpty()) {
             throw new EventNotFoundException("No events found in city: " + location);
        }
    return events;
    }

    public List<Event> getEventsByLocationIgnoreCase(String location) {
        List<Event> events = eventRepository.findByLocationIgnoreCaseContaining(location);
        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found in city: " + location);
        }
         return events;
    }




}
