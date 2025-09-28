package com.eventmanagement.service;


import com.eventmanagement.entity.*;
import com.eventmanagement.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List; 


@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }
    
    public Booking createBooking(Booking booking){
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setStatus("confirmed");
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByEventId(Long eventId){
        return bookingRepository.findByEventId(eventId);
    }

    public List<Booking> getBookingsByUserId(Long userId){
        return bookingRepository.findByUserId(userId);
    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }
    
}
