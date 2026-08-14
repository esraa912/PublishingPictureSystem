package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate that a user has already been archived
 * and therefore cannot perform certain operations.
 *
 * @author esraa
 */
@Getter
public class UserAlreadyArchivedException extends RuntimeException {
    public static final String USER_ALREADY_ARCHIVED_EXCEPTION_MESSAGE = "userAlreadyArchivedException";
    public static final int USER_ALREADY_ARCHIVED_EXCEPTION_CODE = 1008;

    private final String description;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code UserAlreadyArchivedException} with the specified description.
     *
     * @param description a detailed explanation of the archival error
     */
    public UserAlreadyArchivedException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
