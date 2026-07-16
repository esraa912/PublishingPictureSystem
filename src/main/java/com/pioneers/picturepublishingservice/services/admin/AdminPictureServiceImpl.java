package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureStorageException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import jakarta.transaction.Transactional;
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
public class AdminPictureServiceImpl implements AdminPictureService {

    private final PictureRepository pictureRepository;

    @Override
    public List<PictureResponse> getPendingPictures() {
        return pictureRepository.findByStatus(PictureStatus.PENDING)
                .stream()
                .map(PictureMapper::toPictureResponse)
                .toList();
    }

    @Override
    @Transactional
    public void approvePicture(final UUID pictureId) {
        final Picture picture = pictureRepository.findById(pictureId)
                .orElseThrow(() -> new PictureException("Picture is not found"));

        picture.setStatus(PictureStatus.ACCEPTED);

        final String url = buildPictureUrl(picture);

        log.info("Picture's url is {}", url);

        picture.setUrl(url);

        pictureRepository.save(picture);
    }

    private static String buildPictureUrl(Picture picture) {
        return "uploads/" + Paths.get(picture.getFilePath()).getFileName().toString();
    }

    @Override
    @Transactional
    public void rejectPicture(final UUID id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureException("Picture is not found"));

        picture.setStatus(PictureStatus.REJECTED);

        try {
            Path path = Paths.get(picture.getFilePath());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new PictureStorageException("Could not delete file: " + picture.getFilePath(), e);
        }

        pictureRepository.save(picture);
    }
}
