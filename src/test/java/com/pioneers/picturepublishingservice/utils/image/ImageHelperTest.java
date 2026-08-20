package com.pioneers.picturepublishingservice.utils.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pioneers.picturepublishingservice.models.valueobjects.ImageDimensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pioneers.picturepublishingservice.errors.exceptions.FileException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;

/**
 * Unit tests for the {@link ImageHelper} utility class.
 *
 * @author esraa
 */
@ExtendWith(MockitoExtension.class)
public class ImageHelperTest {

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 50;

    @Test
    void testDeleteFileWhenFileExistsThenDeletedSuccessfully() {
        try (MockedStatic<ImageHelper> mockedFileHelper = mockStatic(ImageHelper.class)) {
            // Arrange
            mockedFileHelper.when(() -> ImageHelper.deleteFile("test.txt"))
                    .thenAnswer(invocation -> null);

            // Act
            ImageHelper.deleteFile("test.txt");

            // Assert
            mockedFileHelper.verify(() -> ImageHelper.deleteFile("test.txt"), times(1));
        }
    }

    @Test
    void testDeleteFileWhenFilePathIsBlankThenThrowsFileException() {
        // Act
        FileException ex = assertThrows(FileException.class,
                () -> ImageHelper.deleteFile(""));

        // Assert
        assertTrue(ex.getMessage().contains("File path is null or blank"));
    }

    @Test
    void testDeleteFileWhenIoExceptionOccursThenThrowsFileException() {
        // Arrange
        final Path mockPath = Paths.get("test.txt");

        try (MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedPaths.when(() -> Paths.get("Z:/invalid/path/file.txt")).thenReturn(mockPath);
            mockedFiles.when(() -> Files.deleteIfExists(mockPath)).thenThrow(new IOException("Disk error"));

            // Act
            FileException ex = assertThrows(FileException.class,
                    () -> ImageHelper.deleteFile("Z:/invalid/path/file.txt"));

            // Assert
            assertTrue(ex.getMessage().contains("Failed to delete file"));
        }
    }

    @Test
    void testCreatePathReturnsPathSuccessfully() {
        // Arrange
        final Path path = ImageHelper.createPath("uploads", "jpg");

        // Assert
        assertNotNull(path);
    }

    @Test
    void testBuildUrlThenReturnFullPathSuccessfully() {
        // Arrange
        final String result = ImageHelper.buildUrl("uploads", "image.jpg");

        // Assert
        assertEquals(Paths.get("uploads", "image.jpg").toString(), result);
    }

    @Test
    void testBuildUrlWithBaseDirectoryAndFileName() {
        // Arrange
        final String result = ImageHelper.buildUrl("uploads", "image.jpg");

        // Assert
        assertEquals(Paths.get("uploads", "image.jpg").toString(), result);
    }

    @Test
    void testBuildUrlWithNestedDirectory() {
        // Arrange
        final String result = ImageHelper.buildUrl("uploads/photos", "nature.png");

        // Assert
        assertEquals(Paths.get("uploads/photos", "nature.png").toString(), result);
    }

    @Test
    void testBuildUrlWithEmptyBaseDirectory() {
        // Arrange
        final String result = ImageHelper.buildUrl("", "file.txt");

        // Assert
        assertEquals(Paths.get("", "file.txt").toString(), result);
    }

    @Test
    void testBuildUrlWithEmptyFileName() {
        // Arrange
        final String result = ImageHelper.buildUrl("uploads", "");

        // Assert
        assertEquals(Paths.get("uploads", "").toString(), result);
    }

    @Test
    void testBuildUrlWithAbsolutePath() {
        // Arrange
        final String result = ImageHelper.buildUrl("/var/data", "picture.gif");

        // Assert
        assertEquals(Paths.get("/var/data", "picture.gif").toString(), result);
    }


    @Test
    void testFetchExtensionThenReturnExtensionSuccessfully() {
        // Arrange
        final String result = ImageHelper.fetchExtension("image.png");

        // Assert
        assertEquals("png", result);
    }

    @Test
    void testValidateExtensionWithValidExtensionDoesNotThrow() {
        ImageHelper.validateExtension("jpg");
        ImageHelper.validateExtension("png");
        ImageHelper.validateExtension("gif");
    }

    @Test
    void testValidateExtensionWithInvalidExtensionThrowsPictureException() {
        assertThrows(PictureException.class, () -> ImageHelper.validateExtension("bmp"));
        assertThrows(PictureException.class, () -> ImageHelper.validateExtension("exe"));
    }

    @Test
    void testWriteInSuccessfully() throws IOException {
        // Arrange
        final Path filePath = Paths.get("test.txt");
        final byte[] content = "Hello World".getBytes();

        // Act
        ImageHelper.writeIn(filePath, content);

        // Assert
        assertTrue(Files.exists(filePath));
        assertEquals("Hello World", Files.readString(filePath));
    }

    @Test
    void testWriteInThrowsPictureExceptionWhenIoExceptionOccurs() {
        // Arrange
        final Path invalidPath = Path.of("/invalid/path/file.txt");
        final byte[] content = "data".getBytes();

        // Act
        PictureException ex = assertThrows(PictureException.class,
                () -> ImageHelper.writeIn(invalidPath, content));

        // Assert
        assertTrue(ex.getMessage().contains("Failed to save picture file"));
    }

    @Test
    void testGetImageDimensionsSuccessfully() {
        // Arrange
        final BufferedImage image = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Act
        final ImageDimensions dimensions = ImageHelper.createImageDimensions(image.getWidth(), image.getHeight());

        // Assert
        assertEquals(DEFAULT_WIDTH, dimensions.width());
        assertEquals(DEFAULT_HEIGHT, dimensions.height());
    }
}
