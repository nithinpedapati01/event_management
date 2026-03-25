package com.eventmanagement.service;


import com.eventmanagement.entity.*;
import com.eventmanagement.repository.BookingRepository;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import com.eventmanagement.dto.request.BookingRequest;
import com.eventmanagement.dto.response.BookingResponse;
import com.eventmanagement.exception.ResourceNotFoundException;



@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository, UserRepository userRepository){
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }
    
    private BookingResponse mapToBookingResponse(Booking booking){
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setEventId(booking.getEvent().getId());
        response.setNumberOfTickets(booking.getNumberOfTickets());
        response.setBookingDateTime(booking.getBookingDateTime());
        response.setStatus(booking.getStatus());
        return response;
    }
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest){
        Booking booking = new Booking();
        Event event = eventRepository.findById(bookingRequest.getEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + bookingRequest.getEventId()));
        User user = userRepository.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + bookingRequest.getUserId()));

        booking.setEvent(event);
        booking.setUser(user);
        if(event.getCapacity() < bookingRequest.getNumberOfTickets()){
            throw new IllegalArgumentException("Not enough tickets available");
}
        else{
            event.setCapacity(event.getCapacity() - bookingRequest.getNumberOfTickets());
            eventRepository.save(event);
            booking.setNumberOfTickets(bookingRequest.getNumberOfTickets());
        }
        
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);
        Booking savedBooking = bookingRepository.save(booking);
        return mapToBookingResponse(savedBooking);
    }





    public List<BookingResponse> getAllBookings(){
        return bookingRepository.findAll().stream().map(this::mapToBookingResponse).toList();
    }

    public List<BookingResponse> getBookingsByEventId(Long eventId){
        return bookingRepository.findByEventId(eventId).stream().map(this::mapToBookingResponse).toList();
    }

    public List<BookingResponse> getBookingsByUserId(Long userId){
        return bookingRepository.findByUserId(userId).stream().map(this::mapToBookingResponse).toList();
    }

    public void deleteBooking(Long id){
        if(!bookingRepository.existsById(id)){
            throw new ResourceNotFoundException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }
    
}
