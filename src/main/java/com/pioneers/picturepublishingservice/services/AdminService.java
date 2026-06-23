package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    List<PictureResponse> getPendingPictures();

    void approvePicture(UUID id);

    void rejectPicture(UUID id);
}
