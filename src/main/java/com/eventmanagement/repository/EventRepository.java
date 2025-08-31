package com.eventmanagement.repository;

import com.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Custom finder method to get events after a given date & time
    List<Event> findByDateTimeAfter(LocalDateTime dateTime);

    List<Event> findByLocation(String location);

     List<Event> findByLocationIgnoreCaseContaining(String location);

}
