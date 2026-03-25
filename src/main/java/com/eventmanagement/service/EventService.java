package com.eventmanagement.service;

import com.eventmanagement.entity.Event;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.entity.Status;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.dto.response.EventResponse;
import com.eventmanagement.dto.request.CreateEventRequest;
import org.springframework.stereotype.Service;
import com.eventmanagement.entity.EventType;

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

        private EventResponse mapToEventResponse(Event event) {

        EventResponse response = new EventResponse();

        response.setId(event.getId());
        response.setName(event.getName());
        response.setLocation(event.getLocation());
        response.setCapacity(event.getCapacity());
        response.setEventType(event.getEventType());
        response.setStatus(event.getStatus());
        response.setDateTime(event.getDateTime());

        return response;
    }

    public EventResponse createEvent(CreateEventRequest request) {
        
        Event event  = new Event();
        event.setName(request.getName());
        event.setCapacity(request.getCapacity());
        event.setLocation(request.getLocation());
        event.setEventType(request.getEventType());
        event.setStatus(Status.UPCOMING);
        event.setDateTime(LocalDateTime.parse(request.getDateTime()));

        Event savedEvent = eventRepository.save(event); 
        
        return mapToEventResponse(savedEvent);

    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
            .map(this::mapToEventResponse)
            .toList();
    }

    public List<EventResponse> getCompletedEvents() {
        return eventRepository.findAll().stream()
            .filter(e -> e.getStatus() == Status.COMPLETED)
            .map(this::mapToEventResponse)
            .toList();
    }

    public List<EventResponse> getUpcomingEvents() {
        return eventRepository.findAll().stream()
            .filter(e -> e.getStatus() == Status.UPCOMING || e.getStatus() == Status.ONGOING)
            .map(this::mapToEventResponse)
            .toList();
    }



    public EventResponse getEventById(Long id) {
         Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

            return mapToEventResponse(event);
    }


    public List<EventResponse> getEventsByLocation(String location) {
        return eventRepository.findByLocationIgnoreCase(location).stream()
            .map(this::mapToEventResponse)
            .toList();
    }

    
    public List<EventResponse> searchEvents(LocalDate date, LocalTime time, String location, EventType type, Status status) {
        
        EventType parsedType = type;
        Status parsedStatus = status;
        return eventRepository.findAll()
            .stream()
            .filter(e -> date == null || e.getDateTime().toLocalDate().equals(date))
            .filter(e -> time == null || e.getDateTime().toLocalTime().equals(time))
            .filter(e -> location == null || e.getLocation().equalsIgnoreCase(location))
            .filter(e -> parsedType == null || e.getEventType() == parsedType)
            .filter(e -> parsedStatus == null || e.getStatus() == parsedStatus)
            .map(this::mapToEventResponse)
            .toList();
    }


    public EventResponse updateEventPartially(Long id, Map<String, Object> updates) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        updates.forEach((key, value) -> {
            switch (key) {

                case "name" -> event.setName((String) value);

                case "location" -> event.setLocation((String) value);

                case "capacity" -> event.setCapacity((Integer) value);

                case "eventType" -> 
                    event.setEventType(EventType.valueOf((String) value));

                case "status" -> 
                    event.setStatus(Status.valueOf((String) value));

                case "dateTime" -> 
                    event.setDateTime(LocalDateTime.parse((String) value));
            }
        });

        Event updatedEvent = eventRepository.save(event);

        return mapToEventResponse(updatedEvent);
    }



    public void  deleteEvent(Long id) {
        
        if(!eventRepository.existsById(id)){
            throw new ResourceNotFoundException("Event not found with id " + id);
        }
        else{
            eventRepository.deleteById(id);
        }
        return;
    }
}
