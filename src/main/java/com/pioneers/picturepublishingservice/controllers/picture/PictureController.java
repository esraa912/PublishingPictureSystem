package com.pioneers.picturepublishingservice.controllers.picture;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pioneers.picturepublishingservice.models.dtos.requests.PictureRequest;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.services.picture.PictureService;
import com.pioneers.picturepublishingservice.services.user.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contains APIs for managing picture operations.
 *
 * @author esraa
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
     * @param pictureDto DTO that includes the information of a picture.
     * @throws IOException if an error occurs while writing the file to disk
     *                     or reading the image input stream.
     */
    @PutMapping("/upload")
    public void uploadApi(@ModelAttribute final PictureRequest pictureDto) throws IOException {
        final String methodName = "uploadApi()";
        final UUID userId = authService.getCurrentUserId();

        log.debug("{} - Uploading picture for userId: {} with category: {}",
                methodName, userId, pictureDto.category());

        pictureService.uploadPicture(pictureDto.file(), pictureDto.description(), pictureDto.category(), userId);

        log.info("{} - Uploaded picture successfully with category: {}", methodName, pictureDto.category());
    }

    /**
     * Retrieves detailed information about a specific picture by its id.
     *
     * @param id the unique identifier of the picture.
     * @return Information about a picture by its id.
     */
    @GetMapping("/details")
    public PictureResponse displayDetailsApi(@RequestBody final UUID id) {
        final String methodName = "displayDetailsApi()";
        log.debug("{} - Fetching details for picture Id: {}", methodName, id);

        PictureResponse response = pictureService.displayPictureDetails(id);

        log.info("{} - Retrieved details successfully for picture Id: {}", methodName, id);
        return response;
    }

    /**
     * Retrieves all accepted pictures' URLs from the system.
     *
     * @return List of Picture's Urls Responses.
     */
    @GetMapping("/display-all")
    public List<PictureUrlResponse> displayAllAcceptedPicturesUrlApi() {
        final String methodName = "displayAllAcceptedPicturesUrlApi()";
        log.debug("{} - Fetching all accepted picture URLs", methodName);

        List<PictureUrlResponse> urls = pictureService.displayAllAcceptedPictureUrl();

        log.info("{} - Returning {} accepted picture URLs", methodName, urls.size());
        return urls;
    }
}