package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CredentialsException extends RuntimeException {
    public static final String CREDENTIALS_EXCEPTION_MESSAGE = "CredentialsException";
    public static final int CREDENTIALS_EXCEPTION_CODE = 1001;

    private final String description;

    public CredentialsException(String description) {
        this.description = description;
    }

    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public CredentialsException(String description, Throwable e) {
        super(description, e);
        this.description = description;
    }
}