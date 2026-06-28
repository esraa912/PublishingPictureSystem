package com.pioneers.picturepublishingservice.controllers.admin;

import com.pioneers.picturepublishingservice.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Contains APIs for Authentications for all admins in our system.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminAuthController {
    private final AdminService adminService;

//    @PostMapping("/login")
//    public void loginApi(@RequestBody final UserLogin adminLogin){
//        adminService.login(adminLogin);
//    }

    /**
     * Logout an admin from our system.
     *
     * @param id The unique identifier of the admin to log out.
     */
    @PostMapping("/logout")
    public void logoutApi(@RequestBody final UUID id){
        adminService.logout(id);
    }
}
