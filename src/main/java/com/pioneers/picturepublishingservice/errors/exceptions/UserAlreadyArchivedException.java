package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * Custom runtime exception used to indicate that a user has already been archived
 * in the system and cannot be processed for further operations.
 *
 * @author esraa
 */
@Getter
public class UserAlreadyArchivedException extends  RuntimeException {
    public static final String USER_ALREADY_ARCHIVED_EXCEPTION_MESSAGE = "userAlreadyArchivedException";
    public static final int USER_ALREADY_ARCHIVED_EXCEPTION_CODE = 1008;

    private final String description;
    private final Timestamp timestamp;

    public UserAlreadyArchivedException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
