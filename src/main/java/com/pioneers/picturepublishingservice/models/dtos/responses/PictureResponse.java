package com.pioneers.picturepublishingservice.models.dtos.responses;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

/**
 * A response DTO representing metadata about a stored or processed picture.
 *
 * @param description the description or caption of the picture.
 * @param category the category classification of the picture.
 * @param width the width of the picture in pixels.
 * @param height the height of the picture in pixels.
 */
@Builder
public record PictureResponse(
        String description,
        CATEGORY category,
        int width,
        int height
) {
}