package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate registration-related errors during the user sign-up process.
 *
 * @author esraa
 */
@Getter
public class RegisterException extends RuntimeException {
    public static final String REGISTER_EXCEPTION_MESSAGE = "registrationException";
    public static final int REGISTER_EXCEPTION_CODE = 1007;

    private final String description;
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code RegisterException} with the specified description.
     *
     * @param description a detailed explanation of the Register error
     * @param methodName  the name of the method where the exception happen
     */
    public RegisterException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
