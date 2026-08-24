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
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code LogoutException} with the specified description.
     *
     * @param description a detailed explanation of the Logout error
     * @param methodName  the name of the method where the exception happen
     */
    public LogoutException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
