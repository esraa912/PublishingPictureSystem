package com.pioneers.picturepublishingservice.services.picture;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.enums.Category;

/**
 * Include the contracts for all picture's operations services.
 *
 * @author esraa
 */
public interface PictureService {

    /**
     * Upload a new picture to our system.
     *
     * @param file is the image file to upload.
     * @param description is a short description of the picture.
     * @param category is the category to which the picture belongs.
     * @param userId is the id of the user uploading the picture.
     * @throws IOException if an error occurs while writing the file to disk
     *                     or reading the image input stream.
     */
    void uploadPicture(MultipartFile file, String description, Category category, UUID userId) throws IOException;

    /**
     * Display details of a specific picture by its id.
     *
     * @param id is the unique identifier of the picture.
     * @return a PictureResponse containing the picture's details.
     */
    PictureResponse displayPictureDetails(UUID id);

    /**
     * Display all the Urls of accepted pictures from our system.
     *
     * @return List of Urls of accepted pictures.
     */
    List<PictureUrlResponse> displayAllAcceptedPictureUrl();
}
