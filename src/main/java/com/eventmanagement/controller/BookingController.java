package com.eventmanagement.controller;


import com.eventmanagement.dto.response.BookingResponse;
import com.eventmanagement.service.BookingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import com.eventmanagement.dto.request.BookingRequest;
import org.springframework.http.HttpStatus;




@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService; 
    }

    
     @PostMapping
     public ResponseEntity<BookingResponse> createBooking( @Valid @RequestBody BookingRequest booking) {
        BookingResponse createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
     }

     @GetMapping
     public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
     }

     @GetMapping("/eventBookings/{id}")
     public ResponseEntity<List<BookingResponse>> getBookingsByEventId(@PathVariable Long id){
        List<BookingResponse> bookings = bookingService.getBookingsByEventId(id);
        return ResponseEntity.ok(bookings);
     }

    @GetMapping("/userBookings/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId){
        List<BookingResponse> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
        

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

}
