package com.eventmanagement.repository;

import com.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.eventmanagement.entity.EventType;
import com.eventmanagement.entity.Status; 


public interface EventRepository extends JpaRepository<Event, Long> {

    // Find by location (ignore case)
    List<Event> findByLocationIgnoreCase(String location);

    // Find by location and exact date-time
    List<Event> findByLocationIgnoreCaseAndDateTime(String location, LocalDateTime dateTime);

    // Find by location, date, and type (case-insensitive)
    @Query("SELECT e FROM Event e " +
       "WHERE (:location IS NULL OR LOWER(e.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
       "AND (:date IS NULL OR FUNCTION('DATE', e.dateTime) = :date) " +
       "AND (:time IS NULL OR FUNCTION('TIME', e.dateTime) = :time) " +
       "AND (:type IS NULL OR e.eventType = :type)" +
       "AND (:status IS NULL OR e.status = :status)")
    List<Event> searchEvents(@Param("date") LocalDate date,
                         @Param("time") LocalTime time,
                         @Param("location") String location,
                         @Param("eventType") EventType type,
                         @Param("status") Status status);

    
}
 