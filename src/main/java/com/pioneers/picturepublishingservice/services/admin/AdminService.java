package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;

import java.util.UUID;

/**
 * Include the contracts for all authentication services.
 *
 * @author esraa
 */
public interface AdminService {

    /**
     * Log in an admin to our system.
     *
     * @param adminLogin is the DTO that includes the information to log in the admin.
     */
    void login(UserLogin adminLogin);

    /**
     * Log out an admin from our system.
     *
     * @param id The unique identifier of the admin to log out.
     */
    void logout(UUID id);
}
