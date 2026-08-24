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
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code UserAlreadyArchivedException} with the specified description.
     *
     * @param description a detailed explanation of the archival error
     * @param methodName  the name of the method where the exception happen
     */
    public UserAlreadyArchivedException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
