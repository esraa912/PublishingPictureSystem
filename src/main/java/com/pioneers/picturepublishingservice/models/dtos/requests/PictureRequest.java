package com.pioneers.picturepublishingservice.models.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;

/**
 * Represents a request payload for picture-related operations, such as uploading or creating a new picture entry.
 *
 * @param file        The uploaded {@link MultipartFile} representing the picture.
 * @param description A human-readable description of the picture.
 * @param category    The {@link CATEGORY} enum value indicating the picture's category.
 * @author esraa
 */
public record PictureRequest(
        MultipartFile file,
        String description,
        CATEGORY category
) {
}
