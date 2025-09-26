package com.eventmanagement.repository;

import com.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Find by location (ignore case)
    List<Event> findByLocationIgnoreCase(String location);

    // Find by location and exact date-time
    List<Event> findByLocationIgnoreCaseAndDateTime(String location, LocalDateTime dateTime);

    // Find by location, date, and type (case-insensitive)
    @Query("SELECT e FROM Event e WHERE LOWER(e.location) = LOWER(:location) " +
           "AND FUNCTION('DATE', e.dateTime) = :date " +
           "AND LOWER(e.type) = LOWER(:type)")
    List<Event> findByLocationAndDateAndType(@Param("location") String location,
                                             @Param("date") LocalDate date,
                                             @Param("type") String type,
                                             @Param("status") String status);

    // Flexible search: date, time, location, type all optional
    @Query("SELECT e FROM Event e " +
           "WHERE (:location IS NULL OR LOWER(e.location) = LOWER(:location)) " +
           "AND (:date IS NULL OR FUNCTION('DATE', e.dateTime) = :date) " +
           "AND (:time IS NULL OR FUNCTION('TIME', e.dateTime) = :time) " +
           "AND (:type IS NULL OR LOWER(e.type) = LOWER(:type))")
    List<Event> searchEvents(@Param("date") LocalDate date,
                             @Param("time") LocalTime time,
                             @Param("location") String location,
                             @Param("type") String type,
                             @Param("status") String status);
}
 