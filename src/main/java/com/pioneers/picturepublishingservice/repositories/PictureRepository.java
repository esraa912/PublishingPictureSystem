package com.pioneers.picturepublishingservice.repositories;

import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {

    List<Picture> findByStatus(PICTURE_STATUS status);
}
