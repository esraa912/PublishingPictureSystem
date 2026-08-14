package com.pioneers.picturepublishingservice.services.admin;

import static com.pioneers.picturepublishingservice.utils.file.FileHelper.buildUrl;
import static com.pioneers.picturepublishingservice.utils.file.FileHelper.deleteFile;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link AdminPictureService} that provides
 * administrative operations for managing pictures.
 *
 * @author esraa
 */
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
    public void approvePicture(final UUID id) throws PictureException {
        final String methodName = "approvePicture()";
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{} - Picture is not found", methodName);
                    return new PictureException("Picture is not found");
                });

        picture.acceptStatus();

        final String url = buildUrl("uploads", picture.getFilePath());

        log.info("Picture's url is {}", url);

        picture.assignUrl(url);

        pictureRepository.save(picture);
        log.info("{} - Picture approved successfully", methodName);
    }

    @Override
    @Transactional
    public void rejectPicture(final UUID id) throws PictureException {
        final String methodName = "rejectPicture()";
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{} - Picture is not found", methodName);
                    return new PictureException("Picture is not found");
                });

        picture.rejectStatus();

        deleteFile(picture.getFilePath());
        log.debug("{} - Deleted file at path: {}", methodName, picture.getFilePath());

        pictureRepository.save(picture);
        log.info("{} - Picture rejected successfully", methodName);
    }
}
