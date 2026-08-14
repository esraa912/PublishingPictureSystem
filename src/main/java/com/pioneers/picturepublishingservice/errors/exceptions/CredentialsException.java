package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate credential-related errors
 * during authentication or password verification processes.
 *
 * @author esraa
 */
@Getter
public class CredentialsException extends RuntimeException {
    public static final String CREDENTIALS_EXCEPTION_MESSAGE = "CredentialsException";
    public static final int CREDENTIALS_EXCEPTION_CODE = 1001;

    private final String description;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code CredentialsException} with the given description.
     *
     * @param description a detailed explanation of the credential error
     */
    public CredentialsException(String description) {
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }

    /**
     * Constructs a new {@code CredentialsException} with the given description
     * and underlying cause.
     *
     * @param description a detailed explanation of the credential error
     * @param e           the underlying exception that caused this error
     */
    public CredentialsException(String description, Throwable e) {
        super(description, e);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
