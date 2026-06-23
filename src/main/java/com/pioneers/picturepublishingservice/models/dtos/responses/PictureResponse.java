package com.pioneers.picturepublishingservice.models.dtos.responses;

import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
public record PictureResponse(
        UUID id,
        String description,
        CATEGORY category,
        String filePath,
        String fileType,
        PICTURE_STATUS status,
        User user,
        String userEmail,
        Timestamp createdAt
) {
}