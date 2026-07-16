package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * Custom runtime exception used to indicate logout-related errors in the system.
 *
 * @author esraa
 */
@Getter
public class LogoutException extends  RuntimeException {
    public static final String LOGOUT_EXCEPTION_MESSAGE = "logoutException";
    public static final int LOGOUT_EXCEPTION_CODE = 1003;

    private final String description;
    private final Timestamp timestamp;

    public LogoutException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
