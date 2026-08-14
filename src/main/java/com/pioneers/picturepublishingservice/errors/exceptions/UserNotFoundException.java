package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate that a user could not be found in the system.
 *
 * @author esraa
 */
@Getter
public class UserNotFoundException extends RuntimeException {
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "userNotFoundException";
    public static final int USER_NOT_FOUND_EXCEPTION_CODE = 1009;

    private final String description;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code UserNotFoundException} with the specified description.
     *
     * @param description a detailed explanation of the user not found error
     */
    public UserNotFoundException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
