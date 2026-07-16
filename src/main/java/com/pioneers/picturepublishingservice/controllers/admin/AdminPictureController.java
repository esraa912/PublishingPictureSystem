package com.pioneers.picturepublishingservice.controllers.admin;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.services.admin.AdminPictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Contains APIs for managing administrative operations for pictures in our system.
 *
 * @author esraa
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminPictureController {

    private final AdminPictureService adminPictureServiceService;

    /**
     * Retrieves all pictures that are currently pending approval.
     *
     * @return List of pending pictures from our system.
     */
    @GetMapping("/show-pending-pictures")
    public List<PictureResponse> showPendingPicturesApi() {
        log.info("Fetching all pending pictures...");
        List<PictureResponse> pendingPictures = adminPictureServiceService.getPendingPictures();
        log.info("Found {} pending pictures", pendingPictures.size());

        return pendingPictures;
    }

    /**
     * Approves a picture by its unique identifier
     *
     * @param id the unique identifier of the picture.
     */
    @PutMapping("/approve-picture")
    public void approvePictureApi(@RequestBody final UUID id) {
        log.info("Approving picture with ID: {}", id);
        adminPictureServiceService.approvePicture(id);
        log.info("Picture {} approved successfully", id);
    }

    /**
     * Rejects a picture by its unique identifier
     *
     * @param id the unique identifier of the picture.
     */
    @PutMapping("/reject-picture")
    public void rejectPictureApi(@RequestBody final UUID id) {
        log.info("Rejecting picture with ID: {}", id);
        adminPictureServiceService.rejectPicture(id);
        log.info("Picture {} rejected successfully", id);
    }
}
