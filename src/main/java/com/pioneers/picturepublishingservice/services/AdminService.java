package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService {

//    void login(UserLogin adminLogin);

    void logout(UUID id);

    List<PictureResponse> getPendingPictures();

    void approvePicture(UUID id);

    void rejectPicture(UUID id);

    void deleteUser(UUID id);
}
