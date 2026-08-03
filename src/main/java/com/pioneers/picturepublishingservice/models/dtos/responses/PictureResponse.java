package com.pioneers.picturepublishingservice.models.dtos.responses;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;

import lombok.Builder;

/**
 * Represents a response payload containing metadata about a stored or processed picture.
 *
 * @param description A textual description of the picture.
 * @param category    The {@link CATEGORY} enum value indicating the picture's category.
 * @param width       The width of the picture in pixels.
 * @param height      The height of the picture in pixels.
 * @author esraa
 */
@Builder
public record PictureResponse(
        String description,
        CATEGORY category,
        int width,
        int height
) {
}