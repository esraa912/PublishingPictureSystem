package com.pioneers.picturepublishingservice.utils;

/**
 * Utility class for common String operations.
 *
 * @author esraa
 */
public final class StringUtils {

    private StringUtils() {
        throw new AssertionError("Cannot instantiate the StringUtils");
    }

    /**
     * Checks if the given file path string is {@code null} or blank.
     *
     * @param filePath the file path string to validate
     * @return {@code true} if the string is null or blank, otherwise {@code false}
     */
    public static boolean isNullOrBlank(final String filePath) {
        return filePath == null || filePath.isBlank();
    }
}
