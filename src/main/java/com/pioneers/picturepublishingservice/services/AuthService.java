package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;

import java.util.List;
import java.util.UUID;

public interface AuthService {

    String registerUser(UserSignup userSignup);

    String loginUser(UserLogin userLogin);

    UUID getCurrentUserId();

    void logoutUser(UUID id);

    List<PictureResponse> displayAllAcceptedPicture();
}
