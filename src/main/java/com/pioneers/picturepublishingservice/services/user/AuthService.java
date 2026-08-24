package com.pioneers.picturepublishingservice.services.user;

import java.util.UUID;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.requests.UserSignup;

/**
 * Include the contracts for all authentication services.
 *
 * @author esraa
 */
public interface AuthService {

    /**
     * Sign up a user into our system.
     *
     * @param userSignup DTO that includes the information to sign up the user.
     */
    void registerUser(UserSignup userSignup);

    /**
     * Log in a student into our system.
     *
     * @param userLogin DTO that includes the information to log in the user.
     */
    void loginUser(UserLogin userLogin);

    /**
     * Log out a user from our system.
     *
     * @param id The unique identifier of the user to log out.
     */
    void logoutUser(UUID id);

    /**
     * Retrieve the id of the current user.
     *
     * @return id of the current user.
     */
    UUID getCurrentUserId();
}
