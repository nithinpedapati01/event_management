package com.eventmanagement.controller;


import com.eventmanagement.entity.Booking;
import com.eventmanagement.service.BookingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService; 
    }
    
     @PostMapping
     public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking create = bookingService.createBooking(booking);
        return ResponseEntity.ok(create);
     }

     @GetMapping
     public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        if(bookings != null && !bookings.isEmpty()){
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.noContent().build();
        }
        
     }

     @GetMapping("/eventBookings/{id}")
     public ResponseEntity<List<Booking>> getBookingsByEventId(@PathVariable Long id){
        List<Booking> bookings = bookingService.getBookingsByEventId(id);
        if(bookings != null && !bookings.isEmpty()){
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.noContent().build();
            }
        }

    @GetMapping("/userBookings/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId){
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        if(bookings != null && !bookings.isEmpty()){
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.noContent().build();
            }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

}
