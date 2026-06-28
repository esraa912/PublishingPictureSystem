package com.pioneers.picturepublishingservice.controllers.user;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.services.picture.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contains APIs for managing users operations in our system.
 */
@Slf4j
@RestController
@RequestMapping("/userPicture")
@RequiredArgsConstructor
public class UserPictureController {

    public final PictureService pictureService;

    /**
     * Retrieves all accepted pictures' URLs from the system.
     * @return List of Picture's Urls Responses.
     */
    @GetMapping("/display-all-pictures")
    public List<PictureUrlResponse> displayAllAcceptedPicturesUrlApi(){
        return pictureService.displayAllAcceptedPictureUrl();
    }
}
