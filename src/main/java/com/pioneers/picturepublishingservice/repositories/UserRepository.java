package com.pioneers.picturepublishingservice.repositories;

import com.pioneers.picturepublishingservice.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Contract to interact with the CRUD operations.
 *
 * @author esraa
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find a specific user by its email.
     * @param email is the email of the user.
     * @return the found student from the repository.
     */
    Optional<User> findByEmail(String email);
}
