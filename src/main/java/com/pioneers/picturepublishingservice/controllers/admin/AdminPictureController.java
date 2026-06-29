package com.pioneers.picturepublishingservice.controllers.admin;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.services.admin.AdminPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Contains APIs for managing administrative operations for pictures in our system.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("adminPicture")
public class AdminPictureController {

    private final AdminPictureService adminPictureServiceService;

    /**
     * Retrieves all pictures that are currently pending approval.
     * @return List of pending pictures from our system.
     */
    @GetMapping("/all-pictures")
    public List<PictureResponse> showPendingPicturesApi() {
        return adminPictureServiceService.getPendingPictures();
    }

    /**
     * Approves a picture by its unique identifier
     * @param id the unique identifier of the picture.
     */
    @PutMapping("/approve")
    public void approvePictureApi(@RequestBody final UUID id){
        adminPictureServiceService.approvePicture(id);
    }

    /**
     * Rejects a picture by its unique identifier
     * @param id the unique identifier of the picture.
     */
    @PutMapping("/reject")
    public void rejectPictureApi(@RequestBody final UUID id){
        adminPictureServiceService.rejectPicture(id);
    }
}
