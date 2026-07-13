package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;

import java.util.List;
import java.util.UUID;

/**
 *  Include the contracts for all picture's operations services.
 */
public interface AdminPictureService {

    /**
     * Retrieve all the pending pictures from oir system.
     * @return List of pictures which are pending.
     */
    List<PictureResponse> getPendingPictures();

    /**
     * Approve a picture by its id.
     * @param id is the unique identifier of the picture.
     */
    void approvePicture(UUID id);

    /**
     * Reject a picture by its id.
     * @param id is the unique identifier of the picture.
     */
    void rejectPicture(UUID id);
}
