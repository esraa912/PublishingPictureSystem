package com.pioneers.picturepublishingservice.controllers;

import com.pioneers.picturepublishingservice.auth.CurrentUserProvider;
import com.pioneers.picturepublishingservice.models.dtos.requests.PictureRequest;
import com.pioneers.picturepublishingservice.services.AuthService;
import com.pioneers.picturepublishingservice.services.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("picture")
public class PictureController {

    private final PictureService pictureService;
    private final AuthService authService;

    private final CurrentUserProvider currentUserProvider;

    @PutMapping("/upload")
    public void uploadPictureApi(@ModelAttribute final PictureRequest pictureDto) throws IOException {
//        UUID userId = currentUserProvider.getCurrentUserId();

        UUID userId = authService.getCurrentUserId();

        pictureService.uploadPicture(pictureDto.file(), pictureDto.description(), pictureDto.category(), userId);
    }
}

