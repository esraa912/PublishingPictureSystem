package com.pioneers.picturepublishingservice.controllers.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.services.admin.AdminPictureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        final String methodName = "showPendingPicturesApi()";
        log.debug("{} - Fetching all pending pictures...", methodName);

        final List<PictureResponse> pendingPictures = adminPictureServiceService.getPendingPictures();

        log.info("{} - Found {} pending pictures", methodName, pendingPictures.size());
        return pendingPictures;
    }

    /**
     * Approves a picture by its unique identifier
     *
     * @param id the unique identifier of the picture.
     */
    @PutMapping("/approve-picture")
    public void approvePictureApi(@RequestBody final UUID id) {
        final String methodName = "approvePictureApi()";
        log.debug("{} - Approving picture with id: {}", methodName, id);

        adminPictureServiceService.approvePicture(id);

        log.info("{} - Picture approved successfully with id: {}", methodName, id);
    }

    /**
     * Rejects a picture by its unique identifier
     *
     * @param id the unique identifier of the picture.
     */
    @PutMapping("/reject-picture")
    public void rejectPictureApi(@RequestBody final UUID id) {
        final String methodName = "rejectPictureApi()";
        log.debug("{} - Rejecting picture with id: {}", methodName, id);

        adminPictureServiceService.rejectPicture(id);

        log.info("{} - Picture rejected successfully with id: {}", methodName, id);
    }
}
