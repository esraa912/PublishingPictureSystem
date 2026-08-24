package com.pioneers.picturepublishingservice.utils.files;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.enums.FileType;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that provides helper methods for image file operations.
 *
 * @author esraa
 */
@Slf4j
public final class ImageHelper {

    private static final int MB = 1024 * 1024;

    private ImageHelper() {
        throw new AssertionError("Cannot instantiate the FileHelper");
    }

    /**
     * Validates that the file size does not exceed the 2MB limit.
     *
     * @param fileSize the size of the file in bytes
     * @throws PictureException if the file size exceeds the limit
     */
    public static void validateSize(final long fileSize) {
        final String methodName = "validateSize()";

        if (fileSize > (2 * MB)) {
            throw new PictureException("File size exceeds 2MB limit", methodName);
        }
    }

    /**
     * Validates whether the given file extension is allowed.
     *
     * @param extension the file extension to validate
     * @throws PictureException if the extension is not allowed
     */
    public static void validateExtension(final String extension) {
        FileType.isExtensionAllowed(extension);
    }

    /**
     * Creates an {@link ImageDimensions} object from given width and height.
     *
     * @param widthPixels  the width of the image in pixels
     * @param heightPixels the height of the image in pixels
     * @return an {@link ImageDimensions} instance containing width and heightPixels
     */
    public static ImageDimensions createDimensions(final int widthPixels, final int heightPixels) {
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
