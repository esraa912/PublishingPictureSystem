package com.pioneers.picturepublishingservice.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;

/**
 * Contract to interact with the CRUD operations.
 *
 * @author esraa
 */
public interface PictureRepository extends JpaRepository<Picture, UUID> {

    /**
     * Find all Picture by its status where it's PENDING, ACCEPTED or REJECTED.
     *
     * @param status is the state of the picture.
     * @return all pictures by its status.
     */
    List<Picture> findByStatus(PictureStatus status);
}
