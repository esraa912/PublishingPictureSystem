package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate logout-related errors during the user session termination process.
 *
 * @author esraa
 */
@Getter
public class LogoutException extends RuntimeException {
    public static final String LOGOUT_EXCEPTION_MESSAGE = "logoutException";
    public static final int LOGOUT_EXCEPTION_CODE = 1003;

    private final String description;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code LogoutException} with the specified description.
     *
     * @param description a detailed explanation of the Logout error
     */
    public LogoutException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
