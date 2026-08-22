package com.pioneers.picturepublishingservice.utils.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.enums.FileType;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that provides helper methods for image file operations.
 *
 * @author esraa
 */
@Slf4j
public final class ImageFileHelper {

    private static final int MB = 1024 * 1024;

    private ImageFileHelper() {
        throw new AssertionError("Cannot instantiate the FileHelper");
    }

    /**
     * Validates that the file size does not exceed the 2MB limit.
     *
     * @param fileSize the size of the file in bytes
     * @throws PictureException if the file size exceeds the limit
     */
    public static void validateSize(final long fileSize) {
        if (fileSize > (2 * MB)) {
            throw new PictureException("File size exceeds 2MB limit");
        }
    }

    /**
     * Validates whether the given file extension is allowed.
     *
     * @param extension the file extension to validate
     * @throws PictureException if the extension is not allowed
     */
    public static void validateExtension(final String extension) {
        if (!FileType.isExtensionAllowed(extension)) {
            throw new PictureException("Only jpg, png, gif are allowed");
        }
    }

    /**
     * Writes the given byte content into a file at the specified path.
     *
     * @param filePath the path where the file should be written
     * @param content  the byte array content to write into the file
     * @throws PictureException if the file cannot be saved to the uploads folder
     */
    public static void writeIn(final Path filePath, final byte[] content) throws PictureException {
        final String methodName = "writeIn()";
        try {
            Files.write(filePath, content);
            log.debug("{} - File written successfully at path = [{}]", methodName, filePath);
        } catch (IOException e) {
            log.error("{} - Failed to save picture file at path: [{}]", methodName, filePath);
            throw new PictureException("Failed to save picture file to uploads folder");
        }
    }

    /**
     * Creates an {@link ImageDimensions} object from given width and height.
     *
     * @param widthPixels  the width of the image in pixels
     * @param heightPixels the height of the image in pixels
     * @return an {@link ImageDimensions} instance containing width and heightPixels
     */
    public static ImageDimensions createImageDimensions(final int widthPixels, final int heightPixels) {
        return new ImageDimensions(widthPixels, heightPixels);
    }

    /**
     * Represents image dimensions with width and height.
     *
     * @param width  the width of the image in pixels
     * @param height the height of the image in pixels
     */
    public record ImageDimensions(int width, int height) {
    }
}
