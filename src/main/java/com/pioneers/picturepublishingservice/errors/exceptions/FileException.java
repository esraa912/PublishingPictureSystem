package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate errors related to File operations.
 *
 * @author esraa
 */
@Getter
public class FileException extends RuntimeException {
    public static final String FILE_EXCEPTION_MESSAGE = "fileException";
    public static final int FILE_EXCEPTION_CODE = 1012;

    private final String description;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code LiquibaseException} with the specified description.
     *
     * @param description a detailed explanation of the Liquibase error
     */
    public FileException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
