package com.eventmanagement.repository;

import com.eventmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email (gmail)
    Optional<User> findByEmail(String emvnmail);

    // Check if a user exists by email
    boolean existsByemail(String email);

}

