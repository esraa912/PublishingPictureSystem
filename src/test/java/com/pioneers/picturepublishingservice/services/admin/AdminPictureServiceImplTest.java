package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureNotFoundException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminPictureServiceImplTest {

    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private AdminPictureServiceImpl adminPictureService;

    @Test
    void testGetPendingPictures_WhenPictureStatusIsPending_thenReturnListOfPictureResponses(){
        //Arrange
        Picture picture = Picture.builder()
                .category(CATEGORY.MACHINE)
                .status(PICTURE_STATUS.PENDING)
                .build();

        when(pictureRepository.findByStatus(PICTURE_STATUS.PENDING))
                .thenReturn(List.of(picture));

        //Act
        List<PictureResponse> result = adminPictureService.getPendingPictures();

        //Assert
        assertNotNull(result);
        assertEquals(picture.getCategory(),result.getFirst().category());
        verify(pictureRepository, times(1)).findByStatus(PICTURE_STATUS.PENDING);
    }

    @Test
    void testGetPendingPictures_WhenNoPendingPictures_thenReturnEmptyList(){
        //Arrange
        when(pictureRepository.findByStatus(PICTURE_STATUS.PENDING))
                .thenReturn(Collections.emptyList());

        //Act
        List<PictureResponse> result = adminPictureService.getPendingPictures();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pictureRepository, times(1)).findByStatus(PICTURE_STATUS.PENDING);
    }

    @Test
    void testApprovePicture_WhenPictureExists_ThenChangeStatusToAccepted(){
        //Arrange
        UUID id = UUID.randomUUID();
        Picture picture = Picture.builder()
                .id(id)
                .category(CATEGORY.MACHINE)
                .filePath("uploads/nature.png")
                .status(PICTURE_STATUS.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Act
        adminPictureService.approvePicture(id);

        //Assert
        assertEquals(PICTURE_STATUS.ACCEPTED, picture.getStatus());
        assertEquals(CATEGORY.MACHINE, picture.getCategory());
        assertEquals("C:\\Users\\DELL\\Desktop\\wave8\\picturePublishingService\\uploads\\nature.png",picture.getUrl());
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(1)).save(picture);
    }

    @Test
    void testApprovePicture_WhenPictureIsNotExist_ThenThrowPictureNotFoundException(){
        //Arrange
        UUID id = UUID.randomUUID();
        when(pictureRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(PictureNotFoundException.class, () -> adminPictureService.approvePicture(id));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(any());
    }

    @Test
    void testRejectPicture_WhenPictureAndFileExist_ThenChangeStatusToRejectedAndDeleteFile() throws IOException {
        //Arrange
        UUID id = UUID.randomUUID();
        Path path = Files.createTempFile("nature", ".png");

        Picture picture = Picture.builder()
                .id(id)
                .category(CATEGORY.MACHINE)
                .filePath(path.toString())
                .status(PICTURE_STATUS.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Act
        adminPictureService.rejectPicture(id);

        //Assert
        assertEquals(PICTURE_STATUS.REJECTED, picture.getStatus());
        assertEquals(CATEGORY.MACHINE, picture.getCategory());
        assertFalse(Files.exists(path));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(1)).save(picture);
    }

    @Test
    void testRejectPicture_WhenPictureExistsAndDeletingFileFailed_ThenThrowRuntimeException() throws IOException {
        //Arrange
        UUID id = UUID.randomUUID();

        Picture picture = Picture.builder()
                .id(id)
                .filePath("h:uploads/nature.png")
                .status(PICTURE_STATUS.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> adminPictureService.rejectPicture(id));
        assertTrue(ex.getMessage().contains("Could not delete file:"));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(picture);
    }

    @Test
    void testRejectPicture_WhenPictureNotFound_ThenThrowPictureNotFoundException() {
        //Arrange
        UUID id = UUID.randomUUID();

        Picture picture = Picture.builder()
                .id(id)
                .status(PICTURE_STATUS.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertEquals(PICTURE_STATUS.PENDING, picture.getStatus());
        assertThrows(PictureNotFoundException.class, () -> adminPictureService.rejectPicture(id));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(picture);
    }
}
