package com.pioneers.picturepublishingservice.models.enums;

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
     * @return true if the extension is allowed, false otherwise
     */
    public static boolean isExtensionAllowed(final String extension) {
        for (FileType type : values()) {
            if (type.getExtension().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
