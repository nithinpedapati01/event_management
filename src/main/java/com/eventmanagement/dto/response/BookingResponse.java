package com.eventmanagement.dto.response;

import java.time.LocalDateTime;

import com.eventmanagement.entity.BookingStatus;

import lombok.Data;

@Data
public class BookingResponse {

    private Long bookingId;
    
    private Long eventId;

    private int numberOfTickets;

    private LocalDateTime bookingDateTime;

    private BookingStatus status;

}
