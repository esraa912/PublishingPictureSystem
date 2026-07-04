package com.pioneers.picturepublishingservice.models.dtos.requests;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import org.springframework.web.multipart.MultipartFile;

public record PictureRequest(
        MultipartFile file,
        String description,
        CATEGORY category
) {
}
