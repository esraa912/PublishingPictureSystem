package com.pioneers.picturepublishingservice.models.enums;

import java.util.Arrays;

import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the possible file types to upload picture in the system.
 *
 * @author esraa
 */
@Getter
@RequiredArgsConstructor
public enum FileType {
    JPG("jpg"),
    PNG("png"),
    GIF("gif");

    private final String extension;

    /**
     * Checks if the given extension is allowed.
     *
     * @param extension the file extension to check
     */
    public static void isExtensionAllowed(final String extension) {
        final String methodName = "isExtensionAllowed()";
        Arrays.stream(values())
                .filter(fileType -> fileType.getExtension().equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() -> new PictureException("Extension is not allowed: " + extension, methodName));
    }
}
