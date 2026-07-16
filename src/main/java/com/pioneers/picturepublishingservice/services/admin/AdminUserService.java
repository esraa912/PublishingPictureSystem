package com.pioneers.picturepublishingservice.services.admin;

import java.util.UUID;

/**
 * Include the contracts for all admin operations services on users.
 *
 * @author esraa
 */
public interface AdminUserService {

    /**
     * Delete a specific user from our system by its id.
     * @param id is the unique identifier of the user.
     */
    void deleteUser(UUID id);
}
