package com.pioneers.picturepublishingservice.controllers;

import com.pioneers.picturepublishingservice.models.dtos.requests.UserLogin;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

//    @PostMapping("/login")
//    public void loginApi(@RequestBody final UserLogin adminLogin){
//        adminService.login(adminLogin);
//    }

    @PostMapping("/logout")
    public void logoutApi(@RequestBody final UUID id){
        adminService.logout(id);
    }

    @GetMapping("/all-pictures")
    public List<PictureResponse> showPendingPicturesApi() {
        return adminService.getPendingPictures();
    }

    @PutMapping("/approve")
    public void approvePictureApi(@RequestParam final UUID id){
        adminService.approvePicture(id);
    }

    @PutMapping("/reject")
    public void rejectPictureApi(@RequestParam final UUID id){
        adminService.rejectPicture(id);
    }

    @DeleteMapping("/delete-user")
    public void deleteUserApi(@RequestBody final UUID id){
        adminService.deleteUser(id);
    }
}
