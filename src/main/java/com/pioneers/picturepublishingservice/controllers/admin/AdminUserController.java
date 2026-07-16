package com.pioneers.picturepublishingservice.controllers.admin;

import com.pioneers.picturepublishingservice.services.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Contains APIs for managing administrative operations for users in our system.
 *
 * @author esraa
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * Delete user by its unique identifier
     *
     * @param id the unique identifier of the user.
     */
    @DeleteMapping("/delete-user")
    public void deleteUserApi(@RequestBody final UUID id) {
        log.info("Request received to delete user with ID: {}", id);
        adminUserService.deleteUser(id);
        log.info("User {} deleted successfully", id);    }
}
