package com.pioneers.picturepublishingservice.models.dtos.requests;

import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

public record PictureRequest(
        MultipartFile file,
        UUID id,
        String description,
        String filePath,
        String fileType,
        PICTURE_STATUS status,
        User user,
        CATEGORY category,
        Timestamp createdAt,
        int width,
        int height
) {
}
