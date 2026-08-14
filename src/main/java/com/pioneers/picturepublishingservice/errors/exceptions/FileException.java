package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception thrown when a file deletion operation fails.
 *
 * @author esraa
 */
@Getter
public class FileException extends RuntimeException {
    public static final String FILE_DELETION_EXCEPTION_MESSAGE = "fileDeletionException";
    public static final int FILE_DELETION_EXCEPTION_CODE = 1012;

    private final String description;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code FileException} with the specified description.
     *
     * @param description a detailed explanation of the file error
     */
    public FileException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
