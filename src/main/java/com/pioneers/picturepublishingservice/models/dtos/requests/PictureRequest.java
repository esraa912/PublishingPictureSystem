package com.pioneers.picturepublishingservice.models.dtos.requests;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import org.springframework.web.multipart.MultipartFile;

/**
 * A request DTO used to encapsulate the data required for creating or uploading a picture.
 *
 * @param file the uploaded picture file.
 * @param description the description or caption of the picture.
 * @param category the category classification of the picture.
 */
public record PictureRequest(
        MultipartFile file,
        String description,
        CATEGORY category
) {
}
