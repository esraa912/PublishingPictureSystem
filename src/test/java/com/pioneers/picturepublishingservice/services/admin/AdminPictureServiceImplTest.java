package com.pioneers.picturepublishingservice.services.admin;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureStorageException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findByStatus(PictureStatus.PENDING))
                .thenReturn(List.of(picture));

        //Ack
        List<PictureResponse> result = adminPictureService.getPendingPictures();

        //Assert
        assertNotNull(result);
        assertEquals(picture.getCategory(), result.getFirst().category());
        verify(pictureRepository, times(1)).findByStatus(PictureStatus.PENDING);
    }

    @Test
    void testGetPendingPictures_WhenNoPendingPictures_thenReturnEmptyList(){
        //Arrange
        when(pictureRepository.findByStatus(PictureStatus.PENDING))
                .thenReturn(Collections.emptyList());

        //Ack
        List<PictureResponse> result = adminPictureService.getPendingPictures();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pictureRepository, times(1)).findByStatus(PictureStatus.PENDING);
    }

    @Test
    void testApprovePicture_WhenPictureExists_ThenChangeStatusToAccepted(){
        //Arrange
        UUID id = UUID.randomUUID();
        Picture picture = Picture.builder()
                .id(id)
                .category(CATEGORY.MACHINE)
                .filePath("uploads/nature.png")
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack
        adminPictureService.approvePicture(id);

        //Assert
        assertEquals(PictureStatus.ACCEPTED, picture.getStatus());
        assertEquals(CATEGORY.MACHINE, picture.getCategory());
        assertThat(picture.getUrl()).contains("nature.png");
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(1)).save(picture);
    }

    @Test
    void testApprovePicture_WhenPictureIsNotExist_ThenThrowPictureNotFoundException(){
        //Arrange
        UUID id = UUID.randomUUID();
        when(pictureRepository.findById(id)).thenReturn(Optional.empty());

        //Ack & Assert
        assertThrows(PictureException.class, () -> adminPictureService.approvePicture(id));
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
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack
        adminPictureService.rejectPicture(id);

        //Assert
        assertEquals(PictureStatus.REJECTED, picture.getStatus());
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
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack & Assert
        RuntimeException ex = assertThrows(PictureStorageException.class, () -> adminPictureService.rejectPicture(id));
        assertThat(ex.getMessage()).contains("Could not delete file:");
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(picture);
    }

    @Test
    void testRejectPicture_WhenPictureNotFound_ThenThrowPictureNotFoundException() {
        //Arrange
        UUID id = UUID.randomUUID();

        Picture picture = Picture.builder()
                .id(id)
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.empty());

        //Ack & Assert
        assertEquals(PictureStatus.PENDING, picture.getStatus());
        assertThrows(PictureException.class, () -> adminPictureService.rejectPicture(id));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(picture);
    }
}
