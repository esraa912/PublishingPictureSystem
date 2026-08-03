package com.pioneers.picturepublishingservice.services.picture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.imageio.ImageIO;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureStorageException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import com.pioneers.picturepublishingservice.utils.mappers.PictureMapper;
import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private static final int MB = 1024 * 1024;

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void uploadPicture(
            final MultipartFile file,
            final String description,
            final CATEGORY category,
            final UUID userId
    ) throws IOException {
        final String methodName = "uploadPicture()";
        log.debug("{} - Uploading picture for user Id: {} with category={}", methodName, userId, category);

        if (file.getSize() > 2 * MB) {
            log.error("{} - File size exceeds 2MB limit", methodName);
            throw new PictureException("File size exceeds 2MB limit");
        }

        final String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if (!List.of("jpg", "png", "gif").contains(extension.toLowerCase())) {
            log.error("{} - Invalid file type: {}", methodName, extension);
            throw new PictureException("Only jpg, png, gif are allowed");
        }

        final Path path = createPath(extension);

        try {
            Files.write(path, file.getBytes());
            log.debug("{} - File written successfully at path={}", methodName, path);
        } catch (IOException e) {
            log.error("{} - Failed to save picture file at path: {}", methodName, path);
            throw new PictureStorageException("Failed to save picture file to uploads folder", e);
        }

        final BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        log.debug("{} - Picture dimensions width: {}, height: {}", methodName, width, height);

        final Picture picture = Picture.builder()
                .description(description)
                .filePath(path.toString())
                .fileType(extension)
                .status(PictureStatus.PENDING)
                .userId(userId)
                .category(category)
                .uploadedAt(TimeHelper.currentTimestamp())
                .width(width)
                .height(height)
                .build();

        pictureRepository.save(picture);
        log.info("{} - Picture uploaded successfully at path: {}", methodName, path);
    }

    private static Path createPath(final String extension) {
        final String fileName = UUID.randomUUID() + "." + extension;
        return Paths.get("uploads/" + fileName);
    }

    private static String getExtension(final String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
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
                .map(PictureMapper::toPictureUrlResponse)
                .toList();
    }
}
