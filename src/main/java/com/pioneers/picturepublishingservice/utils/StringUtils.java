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
     * Checks whether a given string is {@code null} or blank.
     *
     * @param str the string to validate
     * @return {@code true} if the string is null or blank, otherwise {@code false}
     */
    public static boolean isNullOrBlank(final String str) {
        return str == null || str.isBlank();
    }
}
