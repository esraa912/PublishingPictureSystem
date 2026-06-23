package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureNotFoundException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final PictureRepository pictureRepository;

    @Override
    public List<PictureResponse> getPendingPictures() {
        return pictureRepository.findByStatus(PICTURE_STATUS.PENDING)
                .stream()
                .map(PictureMapper::toPictureResponse)
                .toList();
    }

    @Override
    public void approvePicture(final UUID id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException("Picture not found with id: " + id));

        picture.setStatus(PICTURE_STATUS.ACCEPTED);
        pictureRepository.save(picture);
    }

    @Override
    public void rejectPicture(final UUID id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException("Picture not found with id: " + id));

        picture.setStatus(PICTURE_STATUS.REJECTED);
        pictureRepository.save(picture);
    }
}
