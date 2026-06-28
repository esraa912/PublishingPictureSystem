package com.pioneers.picturepublishingservice.controllers.picture;

import com.pioneers.picturepublishingservice.models.dtos.requests.PictureRequest;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.services.picture.PictureService;
import com.pioneers.picturepublishingservice.services.user.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Contains APIs for managing picture operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("picture")
public class PictureController {

    private final PictureService pictureService;
    private final AuthService authService;

    /**
     * Uploads new picture to the system.
     *
     * @param pictureDto
     * @throws IOException if an error occurs while writing the file to disk
     *                     or reading the image input stream.
     */
    @PutMapping("/upload")
    public void uploadApi(@ModelAttribute final PictureRequest pictureDto) throws IOException {

        UUID userId = authService.getCurrentUserId();

        pictureService.uploadPicture(pictureDto.file(), pictureDto.description(), pictureDto.category(), userId);

        log.info("Uploaded Picture Successfully!");
    }

    /**
     * Retrieves detailed information about a specific picture by its id.
     * @param id the unique identifier of the picture.
     * @return Information about a picture by its id.
     */
    @GetMapping("/details")
    public PictureResponse displayDetailsApi(@RequestBody final UUID id){
        return pictureService.displayPictureDetails(id);
    }
}

