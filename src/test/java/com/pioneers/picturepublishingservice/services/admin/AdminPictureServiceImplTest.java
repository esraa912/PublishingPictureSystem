package com.pioneers.picturepublishingservice.services.admin;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pioneers.picturepublishingservice.errors.exceptions.FileException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.Category;
import com.pioneers.picturepublishingservice.models.enums.PictureStatus;
import com.pioneers.picturepublishingservice.repositories.PictureRepository;

@ExtendWith(MockitoExtension.class)
class AdminPictureServiceImplTest {

    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private AdminPictureServiceImpl adminPictureService;

    @Test
    void testGetPendingPicturesWhenPictureStatusIsPendingThenReturnListOfPictureResponses() {
        //Arrange
        Picture picture = Picture.builder()
                .category(Category.MACHINE)
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
    void testGetPendingPicturesWhenNoPendingPicturesThenReturnEmptyList() {
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
    void testApprovePictureWhenPictureExistsThenChangeStatusToAccepted() {
        //Arrange
        UUID id = UUID.randomUUID();
        Picture picture = Picture.builder()
                .id(id)
                .category(Category.MACHINE)
                .filePath("uploads/nature.png")
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack
        adminPictureService.approvePicture(id);

        //Assert
        assertEquals(PictureStatus.ACCEPTED, picture.getStatus());
        assertEquals(Category.MACHINE, picture.getCategory());
        assertThat(picture.getUrl()).contains("nature.png");
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(1)).save(picture);
    }

    @Test
    void testApprovePictureWhenPictureIsNotExistThenThrowPictureNotFoundException() {
        //Arrange
        UUID id = UUID.randomUUID();
        when(pictureRepository.findById(id)).thenReturn(Optional.empty());

        //Ack & Assert
        assertThrows(PictureException.class, () -> adminPictureService.approvePicture(id));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(any());
    }

    @Test
    void testRejectPictureWhenPictureAndFileExistThenChangeStatusToRejectedAndDeleteFile() throws IOException {
        //Arrange
        UUID id = UUID.randomUUID();
        Path path = Files.createTempFile("nature", ".png");

        Picture picture = Picture.builder()
                .id(id)
                .category(Category.MACHINE)
                .filePath(path.toString())
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack
        adminPictureService.rejectPicture(id);

        //Assert
        assertEquals(PictureStatus.REJECTED, picture.getStatus());
        assertEquals(Category.MACHINE, picture.getCategory());
        assertFalse(Files.exists(path));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(1)).save(picture);
    }

    @Test
    void testRejectPictureWhenPictureExistsAndFilePathIsWrongAndDeletingFileFailedThenThrowFileException() {
        //Arrange
        UUID id = UUID.randomUUID();

        Picture picture = Picture.builder()
                .id(id)
                .filePath("h:uploads/nature.png")
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack & Assert
        RuntimeException ex = assertThrows(FileException.class, () -> adminPictureService.rejectPicture(id));
        assertTrue(ex.getMessage().contains("Failed to delete file at path:"));
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(picture);
    }

    @Test
    void testRejectPictureWhenPictureExistsAndFilePathIsBlankAndDeletingFileFailedThenThrowFileException() {
        //Arrange
        UUID id = UUID.randomUUID();

        Picture picture = Picture.builder()
                .id(id)
                .filePath("")
                .status(PictureStatus.PENDING)
                .build();

        when(pictureRepository.findById(id)).thenReturn(Optional.of(picture));

        //Ack & Assert
        RuntimeException ex = assertThrows(FileException.class, () -> adminPictureService.rejectPicture(id));
        assertEquals("File path is null or blank", ex.getMessage());
        verify(pictureRepository, times(1)).findById(id);
        verify(pictureRepository, times(0)).save(picture);
    }

    @Test
    void testRejectPictureWhenPictureNotFoundThenThrowPictureException() {
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
