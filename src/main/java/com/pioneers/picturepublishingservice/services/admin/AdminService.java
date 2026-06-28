package com.pioneers.picturepublishingservice.services.admin;

import java.util.UUID;

/**
 * Include the contracts for all authentication services.
 */
public interface AdminService {

//    void login(UserLogin adminLogin);

    /**
     * Log out an admin from our system.
     *
     * @param id The unique identifier of the admin to log out.
     */
    void logout(UUID id);
}
