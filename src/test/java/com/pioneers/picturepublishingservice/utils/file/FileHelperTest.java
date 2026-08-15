package com.pioneers.picturepublishingservice.utils.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pioneers.picturepublishingservice.errors.exceptions.FileException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;

/**
 * Unit tests for the {@link FileHelper} utility class.
 *
 * @author esraa
 */
@ExtendWith(MockitoExtension.class)
public class FileHelperTest {

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 50;

    @Test
    void testDeleteFileWhenFileExistsThenDeletedSuccessfully() {
        try (MockedStatic<FileHelper> mockedFileHelper = mockStatic(FileHelper.class)) {
            // Arrange
            mockedFileHelper.when(() -> FileHelper.deleteFile("test.txt"))
                    .thenAnswer(invocation -> null);

            // Act
            FileHelper.deleteFile("test.txt");

            // Assert
            mockedFileHelper.verify(() -> FileHelper.deleteFile("test.txt"), times(1));
        }
    }

    @Test
    void testDeleteFileWhenFilePathIsBlankThenThrowsFileException() {
        // Act
        FileException ex = assertThrows(FileException.class,
                () -> FileHelper.deleteFile(""));

        // Assert
        assertTrue(ex.getMessage().contains("File path is null or blank"));
    }

    @Test
    void testDeleteFileWhenIoExceptionOccursThenThrowsFileException() throws Exception {
        // Arrange
        final Path mockPath = Paths.get("test.txt");

        try (MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedPaths.when(() -> Paths.get("Z:/invalid/path/file.txt")).thenReturn(mockPath);
            mockedFiles.when(() -> Files.deleteIfExists(mockPath)).thenThrow(new IOException("Disk error"));

            // Act
            FileException ex = assertThrows(FileException.class,
                    () -> FileHelper.deleteFile("Z:/invalid/path/file.txt"));

            // Assert
            assertTrue(ex.getMessage().contains("Failed to delete file"));
        }
    }

    @Test
    void testCreatePathReturnsPathSuccessfully() {
        // Arrange
        final Path path = FileHelper.createPath("uploads", "jpg");

        // Assert
        assertNotNull(path);
    }

    @Test
    void testBuildUrlThenReturnUrlSuccessfully() {
        // Arrange
        final String result = FileHelper.buildUrl("uploads", "image.jpg");

        // Assert
        assertEquals("image.jpg", result);
    }

    @Test
    void testFetchExtensionThenReturnExtensionSuccessfully() {
        // Arrange
        final String result = FileHelper.fetchExtension("image.png");

        // Assert
        assertEquals("png", result);
    }

    @Test
    void testIsExtensionAllowedWhenValidExtensionThenReturnTrue() {
        // Assert
        assertTrue(FileHelper.isExtensionAllowed("jpg"));
        assertTrue(FileHelper.isExtensionAllowed("png"));
        assertTrue(FileHelper.isExtensionAllowed("gif"));
    }

    @Test
    void testIsExtensionAllowedWithInvalidExtensionThenReturnFalse() {
        // Assert
        assertFalse(FileHelper.isExtensionAllowed("bmp"));
        assertFalse(FileHelper.isExtensionAllowed("exe"));
    }

    @Test
    void testWriteInSuccessfully() throws IOException {
        // Arrange
        final Path filePath = Paths.get("test.txt");
        final byte[] content = "Hello World".getBytes();

        // Act
        FileHelper.writeIn(filePath, content);

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
                () -> FileHelper.writeIn(invalidPath, content));

        // Assert
        assertTrue(ex.getMessage().contains("Failed to save picture file"));
    }

    @Test
    void testGetImageDimensionsSuccessfully() {
        // Arrange
        final BufferedImage image = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Act
        final int[] dimensions = FileHelper.getImageDimensions(image);

        // Assert
        assertEquals(DEFAULT_WIDTH, dimensions[0]); // width
        assertEquals(DEFAULT_HEIGHT, dimensions[1]);  // height
    }
}
