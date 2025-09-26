package com.eventmanagement.service;

import com.eventmanagement.entity.Event;
import com.eventmanagement.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        event.setStatus("upcoming");
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getCompletedEvents() {
        return eventRepository.findAll().stream()
            .filter(e -> "completed".equalsIgnoreCase(e.getStatus()) )
            .toList();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findAll().stream()
            .filter(e -> "upcoming".equalsIgnoreCase(e.getStatus()) || "live".equalsIgnoreCase(e.getStatus()))
            .toList();
    }



    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> getEventsByLocation(String location) {
        return eventRepository.findByLocationIgnoreCase(location);
    }

    
    public List<Event> searchEvents(LocalDate date, LocalTime time, String location, String type, String status) {
        return eventRepository.searchEvents(date, time, location, type, status);
    }


    public Event updateEventPartially(Long id, Map<String, Object> updates) {
    return eventRepository.findById(id).map(event -> {
        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> event.setName((String) value);
                case "location" -> event.setLocation((String) value);
                case "capacity" -> event.setCapacity((Integer) value);
                case "type" -> event.setType((String) value);
                case "status" -> event.setStatus((String) value);
                case "dateTime" -> event.setDateTime(LocalDateTime.parse((String) value));
            }
        });
        return eventRepository.save(event);
    }).orElseThrow(() -> new RuntimeException("Event not found"));
    }



    public boolean deleteEvent(Long id) {
        
        if(!eventRepository.existsById(id)){
            return false;
        }
        else{
            eventRepository.deleteById(id);
        }
        return true;
    }
}
