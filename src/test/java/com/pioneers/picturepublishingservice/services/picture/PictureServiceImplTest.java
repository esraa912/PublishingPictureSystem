package com.pioneers.picturepublishingservice.services.picture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.Category;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;
    private static final int THREE_MB = 3 * 1024 * 1024;

    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private PictureServiceImpl pictureService;

    @Test
    void testUploadPictureWhenFileIsValidThenPictureSavedSuccessfully() throws IOException {
        //Arrange
        final UUID userId = UUID.randomUUID();

        BufferedImage img = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", outputStream);

        byte[] fileContent = outputStream.toByteArray();

        MultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", fileContent
        );

        //Ack
        pictureService.uploadPicture(file, "description", Category.NATURE, userId);

        //Assert
        ArgumentCaptor<Picture> captor = ArgumentCaptor.forClass(Picture.class);
        verify(pictureRepository).save(captor.capture());

        Picture savedPicture = captor.getValue();
        assertEquals("description", savedPicture.getDescription());
        assertEquals("jpg", savedPicture.getFileType());
        assertEquals(Category.NATURE, savedPicture.getCategory());
        assertEquals(PictureStatus.PENDING, savedPicture.getStatus());
        assertNotNull(savedPicture.getUploadedAt());
        assertTrue(savedPicture.getWidth() > 0);
        assertTrue(savedPicture.getHeight() > 0);

        verify(pictureRepository, times(1)).save(savedPicture);
    }

    @Test
    void testUploadPictureWhenFileSizeExceedsLimitThenThrowPictureSizeException() {
        //Arrange
        final UUID userId = UUID.randomUUID();

        byte[] largeFileContent = new byte[THREE_MB];
        MultipartFile file = new MockMultipartFile(
                "file", "large.jpg", "image/jpeg", largeFileContent
        );

        //Ack & Assert
        PictureException ex = assertThrows(PictureException.class,
                () -> pictureService.uploadPicture(file, "description", Category.NATURE, userId));

        assertEquals("File size exceeds 2MB limit", ex.getMessage());
        verify(pictureRepository, never()).save(any());
    }

    @Test
    void testUploadPictureWhenFileExtensionIsInvalidThenThrowPictureExtensionException() {
        //Arrange
        final UUID userId = UUID.randomUUID();

        byte[] fileContent = "test-image".getBytes();
        MultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", fileContent
        );

        //Ack & Assert
        assertThrows(PictureException.class,
                () -> pictureService.uploadPicture(file, "description", Category.NATURE, userId));

        verify(pictureRepository, never()).save(any());
    }
}
