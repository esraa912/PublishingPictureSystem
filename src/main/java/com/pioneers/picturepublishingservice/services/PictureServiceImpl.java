package com.pioneers.picturepublishingservice.services;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureExtensionException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureSizeException;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService{
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public void uploadPicture(
            MultipartFile file, String description, CATEGORY category, UUID userId) throws IOException {

        if (file.getSize() > 2 * 1024 * 1024){
            throw new PictureSizeException("File size exceeds 2MB limit");
        }

        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if(!List.of("jpg", "png", "gif").contains(extension.toLowerCase())){
            throw new PictureExtensionException("Only jpg, png, gif are allowed");
        }

        String fileName = UUID.randomUUID() + "." + extension;
        Path path = Paths.get("uploads/" + fileName);
        Files.write(path, file.getBytes());

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        Picture picture = Picture.builder()
                .description(description)
                .filePath(path.toString())
                .fileType(extension)
                .status(PICTURE_STATUS.PENDING)
                .user(userRepository.findById(userId).orElseThrow())
                .category(category)
                .uploadedAt(Timestamp.from(Instant.now()))
                .width(width)
                .height(height)
                .build();

        pictureRepository.save(picture);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
