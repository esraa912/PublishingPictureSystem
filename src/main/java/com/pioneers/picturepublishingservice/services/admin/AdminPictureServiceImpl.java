package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureNotFoundException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPictureServiceImpl implements AdminPictureService{

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

        String url = "C:\\Users\\DELL\\Desktop\\wave8\\picturePublishingService\\uploads\\"
                + Paths.get(picture.getFilePath()).getFileName().toString();

        picture.setUrl(url);

        pictureRepository.save(picture);
    }

    @Override
    public void rejectPicture(final UUID id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException("Picture not found with id: " + id));

        picture.setStatus(PICTURE_STATUS.REJECTED);

        try {
            Path path = Paths.get(picture.getFilePath());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + picture.getFilePath(), e);
        }

        pictureRepository.save(picture);
    }
}
