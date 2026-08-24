package com.pioneers.picturepublishingservice.utils.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.pioneers.picturepublishingservice.errors.exceptions.FileException;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;

import lombok.extern.slf4j.Slf4j;

import static com.pioneers.picturepublishingservice.utils.StringUtils.isNullOrBlank;

/**
 * Utility class that provides helper methods for file and path operations.
 *
 * @author esraa
 */
@Slf4j
public final class FileHelper {

    private FileHelper() {
        throw new AssertionError("Cannot instantiate the FileHelper");
    }

    /**
     * Deletes a file at the given path if it exists.
     *
     * @param filePath the path of the file to delete
     * @throws FileException if the file path is null/blank or if deletion fails
     */
    public static void delete(final String filePath) {
        final String methodName = "deleteFileIfExists()";
        if (isNullOrBlank(filePath)) {
            throw new FileException("File path is null or blank", methodName);
        }

        try {
            final Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (final IOException e) {
            log.error("{} - Failed to delete file at path: [{}] due to [{}]", methodName, filePath, e.getMessage());
            throw new FileException("Failed to delete file at path: " + filePath, methodName);
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
    public static String buildPath(final String baseDirectory, final String fileName) {
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
            throw new FileException("Failed to save picture file to uploads folder", methodName);
        }
    }
}
