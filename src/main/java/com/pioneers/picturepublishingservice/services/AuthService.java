package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;

import java.util.UUID;

public interface AuthService {

    String registerUser(UserSignup userSignup);

    String loginUser(UserLogin userLogin);

    void logoutUser(UUID id);
}
