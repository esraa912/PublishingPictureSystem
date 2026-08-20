package com.pioneers.picturepublishingservice.services.picture;

import static com.pioneers.picturepublishingservice.utils.image.FileHelper.createPath;
import static com.pioneers.picturepublishingservice.utils.image.FileHelper.fetchExtension;
import static com.pioneers.picturepublishingservice.utils.image.ImageFileHelper.validateExtension;
import static com.pioneers.picturepublishingservice.utils.image.ImageFileHelper.validateSize;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.imageio.ImageIO;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.Category;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.models.valueobjects.ImageDimensions;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.utils.image.ImageFileHelper;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link PictureService} that provides core operations
 * for managing pictures in the system.
 *
 * @author esraa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    @Transactional
    public void uploadPicture(
            final MultipartFile file,
            final String description,
            final Category category,
            final UUID userId
    ) throws IOException {
        final String methodName = "uploadPicture()";
        log.debug("{} - Uploading picture for user Id: {} with category={}", methodName, userId, category);

        validateSize(file.getSize());

        final String extension = fetchExtension(Objects.requireNonNull(file.getOriginalFilename()));
        validateExtension(extension);

        final Path path = createPath("uploads", extension);

        ImageFileHelper.writeIn(path, file.getBytes());

        final BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        final ImageDimensions imageDimensions = ImageFileHelper.createImageDimensions(bufferedImage.getWidth(),
                bufferedImage.getHeight()
        );
        log.debug("{} - Picture dimensions widthPixels: {}, heightPixels: {}",
                methodName, imageDimensions.width(), imageDimensions.height());

        final Picture picture = Picture.builder()
                .description(description)
                .filePath(path.toString())
                .fileType(extension)
                .status(PictureStatus.PENDING)
                .userId(userId)
                .category(category)
                .uploadedAt(TimeHelper.currentTimestamp())
                .width(imageDimensions.width())
                .height(imageDimensions.height())
                .build();

        pictureRepository.save(picture);
        log.info("{} - Picture uploaded successfully at path: {}", methodName, path);
    }

    @Override
    public PictureResponse displayPictureDetails(final UUID id) {
        return pictureRepository.findById(id)
                .map(PictureMapper::toPictureResponse)
                .orElseThrow(() -> new PictureException("Picture not found"));
    }

    @Override
    public List<PictureUrlResponse> displayAllAcceptedPictureUrl() {
        return pictureRepository.findByStatus(PictureStatus.ACCEPTED)
                .stream()
                .map(picture -> PictureMapper.toPictureUrlResponse(picture.getUrl()))
                .toList();
    }
}
