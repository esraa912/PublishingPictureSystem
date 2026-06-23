package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface PictureService {
    void uploadPicture(MultipartFile file, String description, CATEGORY category, UUID userId) throws IOException;
}
