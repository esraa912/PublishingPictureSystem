package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate errors related to File operations.
 *
 * @author esraa
 */
@Getter
public class FileException extends RuntimeException {
    public static final String FILE_EXCEPTION_MESSAGE = "fileException";
    public static final int FILE_EXCEPTION_CODE = 1012;

    private final String description;
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code CredentialsException} with the given description.
     *
     * @param description a detailed explanation of the credential error
     * @param methodName the name of the method where the exception happen
     */
    public FileException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
