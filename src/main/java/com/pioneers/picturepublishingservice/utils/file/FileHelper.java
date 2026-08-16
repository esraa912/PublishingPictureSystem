package com.pioneers.picturepublishingservice.utils.file;

import static com.pioneers.picturepublishingservice.utils.StringUtils.isNullOrBlank;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.UUID;

import com.pioneers.picturepublishingservice.models.enums.FileType;
import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that provides helper methods for file and path operations.
 *
 * @author esraa
 */
@Slf4j
public final class FileHelper {

    private static final int MB = 1024 * 1024;

    private FileHelper() {
        throw new AssertionError("Cannot instantiate the FileHelper");
    }

    /**
     * Deletes a file at the given path if it exists.
     *
     * @param filePath the path of the file to delete
     * @throws PictureException if the file path is null/blank or if deletion fails
     */
    public static void deleteFile(final String filePath) throws PictureException {
        final String methodName = "deleteFileIfExists()";
        if (isNullOrBlank(filePath)) {
            throw new PictureException("File path is null or blank");
        }

        try {
            final Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (final IOException e) {
            log.error("{} - Failed to delete file at path: [{}] due to [{}]", methodName, filePath, e.getMessage());
            throw new PictureException("Failed to delete file at path: " + filePath);
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
        final String fileName = UUID.randomUUID() + "." + extension;
        return Paths.get(basePath, fileName);
    }

    /**
     * Builds a URL-like string by combining a base file path and a relative file path.
     *
     * @param baseFile the base file path
     * @param filePath the relative file path
     * @return the resulting file name as a string
     */
    public static String buildUrl(final String baseFile, final String filePath) {
        return Paths.get(baseFile, filePath).getFileName().toString();
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
     * Checks if the file size exceeds the 2MB limit.
     *
     * @param fileSize the size of the file in bytes
     * @return true if the file size exceeds 2MB, false otherwise
     */
    public static boolean isSizeExceeded(final long fileSize) {
        return fileSize > (2 * MB);
    }

    /**
     * Validates whether the given file extension is allowed.
     *
     * @param extension the file extension to validate
     * @return true if the extension is allowed, false otherwise
     */
    public static boolean isExtensionAllowed(final String extension) {
        return FileType.isExtensionAllowed(extension);
    }

    /**
     * Writes the given byte content into a file at the specified path.
     *
     * @param filePath the path where the file should be written
     * @param content  the byte array content to write into the file
     * @throws com.pioneers.picturepublishingservice.errors.exceptions.PictureException if the file cannot be saved to the uploads folder
     */
    public static void writeIn(final Path filePath, final byte[] content) throws com.pioneers.picturepublishingservice.errors.exceptions.PictureException {
        final String methodName = "writeIn()";
        try {
            Files.write(filePath, content);
            log.debug("{} - File written successfully at path={}", methodName, filePath);
        } catch (IOException e) {
            log.error("{} - Failed to save picture file at path: {}", methodName, filePath);
            throw new com.pioneers.picturepublishingservice.errors.exceptions.PictureException("Failed to save picture file to uploads folder");
        }
    }

    /**
     * Extracts the dimensions (width and height) of a given {@link BufferedImage}.
     *
     * @param bufferedImage the image object to analyze
     * @return an int array containing width at index 0 and height at index 1
     */
    public static int[] getImageDimensions(final BufferedImage bufferedImage) {
        final int widthPixels = bufferedImage.getWidth();
        final int heightPixels = bufferedImage.getHeight();
        return new int[]{widthPixels, heightPixels};
    }

    /**
     * Custom runtime exception thrown when a file deletion operation fails.
     *
     * @author esraa
     */
    @Getter
    public static class PictureException extends RuntimeException {
        public static final String PICTURE_EXCEPTION_MESSAGE = "fileDeletionException";
        public static final int PICTURE_EXCEPTION_CODE = 1012;

        private final String description;
        private final Timestamp timestamp;

        /**
         * Constructs a new {@code FileException} with the specified description.
         *
         * @param description a detailed explanation of the file error
         */
        public PictureException(String description) {
            super(description);
            this.description = description;
            this.timestamp = TimeHelper.currentTimestamp();
        }
    }
}
