package com.pioneers.picturepublishingservice.models.dtos.responses;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record PictureResponse(
        String description,
        CATEGORY category,
        int width,
        int height
) {
}