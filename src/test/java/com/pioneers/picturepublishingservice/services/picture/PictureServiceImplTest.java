package com.pioneers.picturepublishingservice.services.picture;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.entities.User;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import com.pioneers.picturepublishingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PictureServiceImpl pictureService;

    @Test
    void testUploadPicture_WhenFileIsValid_ThenPictureSavedSuccessfully() throws IOException {
        //Arrange
        final UUID userId = UUID.randomUUID();
        final User user = User.builder().id(userId).build();

        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", outputStream);

        byte[] fileContent = outputStream.toByteArray();

        MultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", fileContent
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //Ack
        pictureService.uploadPicture(file, "description", CATEGORY.NATURE, userId);

        //Assert
        ArgumentCaptor<Picture> captor = ArgumentCaptor.forClass(Picture.class);
        verify(pictureRepository).save(captor.capture());

        Picture savedPicture = captor.getValue();
        assertEquals("description", savedPicture.getDescription());
        assertEquals("jpg", savedPicture.getFileType());
        assertEquals(CATEGORY.NATURE, savedPicture.getCategory());
        assertEquals(PictureStatus.PENDING, savedPicture.getStatus());
        assertNotNull(savedPicture.getUploadedAt());
        assertTrue(savedPicture.getWidth() > 0);
        assertTrue(savedPicture.getHeight() > 0);

        verify(pictureRepository, times(1)).save(savedPicture);
    }

    @Test
    void testUploadPicture_WhenFileSizeExceedsLimit_ThenThrowPictureSizeException() {
        //Arrange
        final UUID userId = UUID.randomUUID();

        byte[] largeFileContent = new byte[3 * 1024 * 1024];
        MultipartFile file = new MockMultipartFile(
                "file", "large.jpg", "image/jpeg", largeFileContent
        );

        //Ack & Assert
        PictureException ex = assertThrows(PictureException.class,
                () -> pictureService.uploadPicture(file, "description", CATEGORY.NATURE, userId));

        assertEquals("File size exceeds 2MB limit", ex.getMessage());
        verify(pictureRepository, never()).save(any());
    }

    @Test
    void testUploadPicture_WhenFileExtensionIsInvalid_ThenThrowPictureExtensionException(){
        //Arrange
        final UUID userId = UUID.randomUUID();

        byte[] fileContent = "test-image".getBytes();
        MultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", fileContent
        );

        //Ack & Assert
        assertThrows(PictureException.class,
                () -> pictureService.uploadPicture(file, "description", CATEGORY.NATURE, userId));

        verify(pictureRepository, never()).save(any());
    }
}
