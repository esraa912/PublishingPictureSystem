package com.pioneers.picturepublishingservice.utils.image;

import static com.pioneers.picturepublishingservice.utils.StringUtils.isNullOrBlank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.pioneers.picturepublishingservice.errors.exceptions.FileException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;
import com.pioneers.picturepublishingservice.models.enums.FileType;
import com.pioneers.picturepublishingservice.models.valueobjects.ImageDimensions;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that provides helper methods for file and path operations.
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
     * Deletes a file at the given path if it exists.
     *
     * @param filePath the path of the file to delete
     * @throws FileException if the file path is null/blank or if deletion fails
     */
    public static void deleteFile(final String filePath) throws FileException {
        final String methodName = "deleteFileIfExists()";
        if (isNullOrBlank(filePath)) {
            throw new FileException("File path is null or blank");
        }

        try {
            final Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (final IOException e) {
            log.error("{} - Failed to delete file at path: [{}] due to [{}]", methodName, filePath, e.getMessage());
            throw new FileException("Failed to delete file at path: " + filePath);
        }
    }

    /**
     * Creates a new {@link Path} with a randomly generated file name
     * using a UUID and the provided extension.
     *
     * @param basePath  the base directory where the file should be created
     * @param extension the file extension
     * @return a {@link Path} pointing to the generated file
     */
    public static Path createPath(final String basePath, final String extension) {
        final String fileName = UUID.randomUUID()
                + "." + extension;
        return Paths.get(basePath, fileName);
    }

    /**
     * Builds a URL-like string by combining a base directory path and a file name.
     *
     * @param baseDirectory the base directory path
     * @param fileName      the file name to append
     * @return the full combined path as a string
     */
    public static String buildUrl(final String baseDirectory, final String fileName) {
        return Paths.get(baseDirectory, fileName).toString();
    }

    /**
     * Extracts the file extension from a given filename.
     *
     * @param filename the name of the file
     * @return the file extension
     */
    public static String fetchExtension(final String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
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
            log.debug("{} - File written successfully at path={}", methodName, filePath);
        } catch (IOException e) {
            log.error("{} - Failed to save picture file at path: {}", methodName, filePath);
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
}
