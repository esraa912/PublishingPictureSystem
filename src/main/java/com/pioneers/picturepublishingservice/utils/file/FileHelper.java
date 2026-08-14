package com.pioneers.picturepublishingservice.utils.file;

import static com.pioneers.picturepublishingservice.utils.StringUtils.isNullOrBlank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.pioneers.picturepublishingservice.errors.exceptions.FileException;

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
    // TODO: Create Enum for the extensions.
    public static boolean isExtensionAllowed(final String extension) {
        return List.of("jpg", "png", "gif").contains(extension.toLowerCase());
    }
}
