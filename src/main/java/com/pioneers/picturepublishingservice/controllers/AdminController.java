package com.pioneers.picturepublishingservice.controllers;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import com.pioneers.picturepublishingservice.services.AdminService;
import com.pioneers.picturepublishingservice.services.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/all-pictures")
    public String showPendingPictures(Model model) {
        List<PictureResponse> pendingPictures = adminService.getPendingPictures();
        model.addAttribute("pictures", pendingPictures);
        return "admin";
    }

    @PutMapping("/approve")
    public void approvePicture(@RequestParam final UUID id){
        adminService.approvePicture(id);
    }

    @PutMapping("/reject")
    public void rejectPicture(@RequestParam final UUID id){
        adminService.rejectPicture(id);
    }
}
