package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * Custom runtime exception used to represent errors that occur during user registration operations.
 *
 * @author esraa
 */
@Getter
public class RegisterException extends  RuntimeException {
    public static final String REGISTER_EXCEPTION_MESSAGE = "registrationException";
    public static final int REGISTER_EXCEPTION_CODE = 1007;

    private final String description;
    private final Timestamp timestamp;

    public RegisterException( String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
