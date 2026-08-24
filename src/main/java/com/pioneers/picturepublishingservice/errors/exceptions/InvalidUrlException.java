package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate errors related to invalid or malformed URLs.
 *
 * @author esraa
 */
@Getter
public class InvalidUrlException extends RuntimeException {
    public static final String INVALID_URL_EXCEPTION_MESSAGE = "invalidUrlException";
    public static final int INVALID_URL_EXCEPTION_CODE = 1013;

    private final String description;
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code InvalidUrlException} with the specified description.
     *
     * @param description a detailed explanation of the invalid URL error
     * @param methodName  the name of the method where the exception happen
     */
    public InvalidUrlException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
